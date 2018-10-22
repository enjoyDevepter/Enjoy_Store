package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerHGoodsConfirmComponent;
import cn.ehanmy.hospital.di.module.HGoodsConfirmModule;
import cn.ehanmy.hospital.mvp.contract.HGoodsConfirmContract;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.presenter.HGoodsConfirmPresenter;
import cn.ehanmy.hospital.mvp.ui.widget.MoneyView;
import cn.ehanmy.hospital.util.CacheUtil;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HGoodsConfirmActivity extends BaseActivity<HGoodsConfirmPresenter> implements HGoodsConfirmContract.View, View.OnClickListener {

    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.member_code)
    TextView memberCodeTV;
    @BindView(R.id.hospital_layout)
    View hospitalV;
    @BindView(R.id.hosptial)
    TextView hosptialTV;
    @BindView(R.id.remark)
    EditText remarkET;
    @BindView(R.id.image)
    ImageView imageIV;
    @BindView(R.id.name)
    TextView nameTV;
    @BindView(R.id.price)
    MoneyView priceMV;
    @BindView(R.id.confirm)
    View confirmV;
    @BindView(R.id.time_layout)
    View timeV;
    @BindView(R.id.time)
    TextView timeTV;
    @Inject
    ImageLoader mImageLoader;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerHGoodsConfirmComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .hGoodsConfirmModule(new HGoodsConfirmModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_hgoods_confirm; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titleTV.setText("确认预约");
        backV.setOnClickListener(this);
        confirmV.setOnClickListener(this);
        hospitalV.setOnClickListener(this);
        timeV.setOnClickListener(this);
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        memberCodeTV.setText(memberBean.getUserName());
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
                break;
            case R.id.hospital_layout:
                break;
            case R.id.time_layout:
                break;
            case R.id.confirm:
                break;
        }
    }
}
