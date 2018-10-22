package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerLoginComponent;
import cn.ehanmy.hospital.di.module.LoginModule;
import cn.ehanmy.hospital.mvp.contract.LoginContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.store.StoreBean;
import cn.ehanmy.hospital.mvp.presenter.LoginPresenter;
import cn.ehanmy.hospital.util.CacheUtil;
import cn.ehanmy.hospital.util.SPUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    @BindView(R.id.username)
    EditText userNameET;
    @BindView(R.id.password)
    EditText passwordET;
    @BindView(R.id.login)
    View loginV;
    @Inject
    RxPermissions mRxPermissions;

    @BindView(R.id.parent)
    View parent;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        UserBean spUserbean = SPUtils.get(SPUtils.KEY_FOR_USER_INFO, new UserBean("","",""));
        if((spUserbean != null) && (!TextUtils.isEmpty(spUserbean.getToken()))){
            StoreBean value = SPUtils.get(SPUtils.KEY_FOR_HOSPITAL_INFO, new StoreBean());
            if((value != null) && (!TextUtils.isEmpty(value.getStoreId()))){
                CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER,spUserbean);
                CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_LOGIN_NAME,SPUtils.get(SPUtils.KEY_FOR_USER_NAME,""));
                CacheUtil.saveConstant(CacheUtil.CACHE_KEY_STORE_INFO, value);
                goMainPage();
                return;
            }
        }
        loginV.setOnClickListener(this);
        parent.setOnClickListener(this);
        mPresenter.requestPermissions();
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
            case R.id.login:
                mPresenter.login(userNameET.getText().toString(), passwordET.getText().toString());
                break;
            case R.id.parent:
                hideImm();
                break;
        }
    }

    @Override
    public boolean useImmersive() {
        return false;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void goMainPage() {
        ArmsUtils.startActivity(MainActivity.class);
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }
}
