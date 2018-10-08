package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerSafeSettingComponent;
import cn.ehanmy.hospital.di.module.SafeSettingModule;
import cn.ehanmy.hospital.mvp.contract.SafeSettingContract;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitaInfoBean;
import cn.ehanmy.hospital.mvp.presenter.SafeSettingPresenter;
import cn.ehanmy.hospital.util.CacheUtil;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class SafeSettingActivity extends BaseActivity<SafeSettingPresenter> implements SafeSettingContract.View {

    @BindView(R.id.go_to_change_password)
    View go_to_change_password;
    @BindView(R.id.exit)
    View exit;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.id)
    TextView id;

    @BindView(R.id.title_Layout)
    View title;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSafeSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .safeSettingModule(new SafeSettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_safe_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title, this, "安全设置");
        go_to_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.startActivity(ChangePasswordActivity.class);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_LOGIN_NAME,null);
                CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER,null);
                CacheUtil.saveConstant(CacheUtil.CACHE_KEY_MEMBER,null);
                CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_LOGIN_NAME,null);
                Intent intent = new Intent(SafeSettingActivity.this,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ArmsUtils.startActivity(intent);
            }
        });

        HospitaInfoBean hospitaInfoBean = (HospitaInfoBean) CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO);
        id.setText("" + hospitaInfoBean.getHospitalId());
        name.setText("" + hospitaInfoBean.getName());
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
}
