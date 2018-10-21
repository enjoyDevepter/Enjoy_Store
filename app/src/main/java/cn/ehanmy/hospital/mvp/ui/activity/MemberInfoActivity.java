package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerMemberInfoComponent;
import cn.ehanmy.hospital.di.module.MemberInfoModule;
import cn.ehanmy.hospital.mvp.contract.MemberInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.presenter.MemberInfoPresenter;

import cn.ehanmy.hospital.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MemberInfoActivity extends BaseActivity<MemberInfoPresenter> implements MemberInfoContract.View {

    public static final String KEY_FOR_MEMBER_ID = "key_for_member_id";
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.member_name)
    TextView member_name;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.addr)
    TextView addr;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMemberInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .memberInfoModule(new MemberInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_member_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    public void updateMemberInfo(MemberBean memberBean){
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(memberBean.getHeadImage())
                        .imageView(image)
                        .build());
        member_name.setText(memberBean.getUserName());
        name.setText(memberBean.getRealName());
        phone.setText(memberBean.getMobile());
        String sex = memberBean.getSex();
        if("0".equals(sex)){
            sex = "保密";
        }else if("1".equals(sex)){
            sex = "男";
        }else{
            sex = "女";
        }
        this.sex.setText(sex);
        area.setText(memberBean.getCity().getName());
        addr.setText(memberBean.getAddress());
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

    public Activity getActivity(){
        return this;
    }
}
