package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerChangePasswordComponent;
import cn.ehanmy.hospital.di.module.ChangePasswordModule;
import cn.ehanmy.hospital.mvp.contract.ChangePasswordContract;
import cn.ehanmy.hospital.mvp.presenter.ChangePasswordPresenter;
import cn.ehanmy.hospital.util.EdittextUtil;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter> implements ChangePasswordContract.View {

    @BindView(R.id.title_Layout)
    View title_Layout;
    @BindView(R.id.make_sure_btn)
    View make_sure_btn;

    @BindView(R.id.old_password)
    EditText old_password;

    @BindView(R.id.new_password)
    EditText new_password;
    @BindView(R.id.confirm_password)
    EditText confirm_password;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChangePasswordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .changePasswordModule(new ChangePasswordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_password; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title_Layout,this,"安全设置");
        make_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EdittextUtil.isEmpty(old_password)){
                    showMessage("请输入原密码");
                    return;
                }

                if(EdittextUtil.isEmpty(new_password)){
                    showMessage("请输入新密码");
                    return;
                }

                if(EdittextUtil.isEmpty(confirm_password)){
                    showMessage("请重复新密码");
                    return;
                }

                mPresenter.changePassword(old_password.getText().toString(),
                        new_password.getText().toString(),
                        confirm_password.getText().toString());
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
}
