package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerMyStoreComponent;
import cn.ehanmy.hospital.di.module.MyStoreModule;
import cn.ehanmy.hospital.mvp.contract.MyStoreContract;
import cn.ehanmy.hospital.mvp.model.entity.store.StoreBean;
import cn.ehanmy.hospital.mvp.presenter.MyStorePresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.ui.adapter.MainAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.SpacesItemDecoration;
import cn.ehanmy.hospital.util.CacheUtil;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MyStoreActivity extends BaseActivity<MyStorePresenter> implements View.OnClickListener,MyStoreContract.View,DefaultAdapter.OnRecyclerViewItemClickListener {


    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.setting)
    View settingV;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    MainAdapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyStoreComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myStoreModule(new MyStoreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_store; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titleTV.setText("我的店铺");
        settingV.setVisibility(View.VISIBLE);
        settingV.setOnClickListener(this);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, ArmsUtils.getDimens(ArmsUtils.getContext(), R.dimen.main_item_space)));
        ArmsUtils.configRecyclerView(recyclerView, mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        backV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killMyself();
            }
        });
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        Class<? extends Activity> targetActivity = null;
        switch (position) {
            case 0:  // 店铺信息
                targetActivity = HospitalInfoActivity.class;
                break;
            case 1:
                // 活动信息
                targetActivity = ActivityInfoActivity.class;
                break;
            case 2:
//                 本店会员
                break;
            case 3:
                // 项目设置
                targetActivity = ProjectSettingActivity.class;
                break;
        }

        if (targetActivity == null) {
            showMessage("功能尚未实现");
        } else {
            ArmsUtils.startActivity(targetActivity);
        }
    }
}
