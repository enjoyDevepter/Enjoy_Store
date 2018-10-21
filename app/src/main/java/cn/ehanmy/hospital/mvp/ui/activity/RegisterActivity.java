package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerRegisterComponent;
import cn.ehanmy.hospital.di.module.RegisterModule;
import cn.ehanmy.hospital.mvp.contract.RegisterContract;
import cn.ehanmy.hospital.mvp.presenter.RegisterPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.CodeAdapter;

import static com.jess.arms.utils.ArmsUtils.getContext;
import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View, View.OnClickListener {

    private static final int time_limit = 60;

    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.oneStep)
    View stepOneContentV;
    @BindView(R.id.username)
    EditText userNameET;
    @BindView(R.id.validate)
    EditText validateET;
    @BindView(R.id.get_validate)
    TextView getValidateV;
    @BindView(R.id.register)
    View registerV;

    @BindView(R.id.step_name_layout)
    View stepNameLayoutV;
    @BindView(R.id.step_name)
    View stepNameV;
    @BindView(R.id.twoStep)
    View stepTwoContentV;
    @BindView(R.id.name)
    EditText nameET;
    @BindView(R.id.male_layout)
    View maleLayoutV;
    @BindView(R.id.male)
    View maleV;
    @BindView(R.id.female_layout)
    View femaleLayoutV;
    @BindView(R.id.female)
    View femaleV;
    @BindView(R.id.birth)
    TextView birthTV;
    @BindView(R.id.invite)
    EditText inviteET;
    @BindView(R.id.prev)
    View prevV;
    @BindView(R.id.next)
    View nextV;

    @BindView(R.id.step_last_layout)
    View stepLastLayoutV;
    @BindView(R.id.step_last)
    View stepLastV;

    @BindView(R.id.info_layout)
    View infoLayoutV;
    @BindView(R.id.info)
    TextView infoTV;
    @BindView(R.id.info_btn)
    TextView infoBTNV;

    @BindView(R.id.invite_layout)
    TextView inviteTV;
    @BindView(R.id.inviteTypes)
    RecyclerView inviteTypesRV;
    @BindView(R.id.mask_invite)
    View inviteMaskV;
    @BindView(R.id.close)
    View closeV;
    @BindView(R.id.mask_birth)
    ViewGroup birthMaskV;
    @BindView(R.id.time)
    DatePicker timeDP;
    @Inject
    RecyclerView.Adapter mAdapter;


    private int time = time_limit;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titleTV.setText("注册会员");
        backV.setOnClickListener(this);

        getValidateV.setOnClickListener(this);
        registerV.setOnClickListener(this);
        maleV.setSelected(true);
        maleLayoutV.setOnClickListener(this);
        femaleLayoutV.setOnClickListener(this);
        inviteTV.setOnClickListener(this);
        inviteMaskV.setOnClickListener(this);
        prevV.setOnClickListener(this);
        nextV.setOnClickListener(this);
        birthTV.setOnClickListener(this);
        birthMaskV.setOnClickListener(this);
        closeV.setOnClickListener(this);
        infoBTNV.setOnClickListener(this);
        getCache().put("sex", "1");
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
            case R.id.get_validate:
                getVerify();
                break;
            case R.id.register:
                register();
                break;
            case R.id.male_layout:
                getCache().put("sex", "1");
                maleV.setSelected(true);
                femaleV.setSelected(false);
                break;
            case R.id.female_layout:
                getCache().put("sex", "2");
                maleV.setSelected(false);
                femaleV.setSelected(true);
                break;
            case R.id.prev:
                goBack();
                break;
            case R.id.next:
                registerLast();
                break;
            case R.id.info_btn:
                if (nextV.isShown()) {
                    ArmsUtils.startActivity(BuyCenterActivity.class);
                } else {
                    hasRegister(false);
                }
                break;
            case R.id.mask_invite:
            case R.id.invite_layout:
                showInviteTypes();
                break;
            case R.id.birth:
            case R.id.mask_birth:
            case R.id.close:
                showBirth();
                break;
        }
    }

    private void showBirth() {
        if (!birthMaskV.isShown()) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int monthOfYear = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            timeDP.init(year, monthOfYear, dayOfMonth, new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    String birthday = year + "-" + (monthOfYear < 10 ? "0" + monthOfYear : monthOfYear) + "-" + dayOfMonth;
                    birthTV.setText(birthday);
                    getCache().put("birthday", birthday);
                }
            });
            birthMaskV.setVisibility(View.VISIBLE);
            birthMaskV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_in));
            timeDP.setVisibility(View.VISIBLE);
            timeDP.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.for_butom_in));
        } else {
            birthMaskV.setVisibility(View.GONE);
            birthMaskV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_out));
            timeDP.setVisibility(View.GONE);
            timeDP.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.for_buttom_out));
        }
    }


    private void showInviteTypes() {
        if (!inviteMaskV.isShown()) {
            ArmsUtils.configRecyclerView(inviteTypesRV, new LinearLayoutManager(this));
            inviteTypesRV.setAdapter(mAdapter);
            ((CodeAdapter) mAdapter).setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int viewType, Object data, int position) {
                    inviteTV.setText(String.valueOf(data));
                    switch (position) {
                        case 0:
                            provideCache().put("type", "");
                            break;
                        case 1:
                            provideCache().put("type", "1");
                            break;
                        case 2:
                            provideCache().put("type", "2");
                            break;
                    }
                    showInviteTypes();
                }
            });
            inviteMaskV.setVisibility(View.VISIBLE);
            inviteMaskV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_in));
            inviteTypesRV.setVisibility(View.VISIBLE);
            inviteTypesRV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.for_butom_in));
        } else {
            inviteMaskV.setVisibility(View.GONE);
            inviteMaskV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_out));
            inviteTypesRV.setVisibility(View.GONE);
            inviteTypesRV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.for_buttom_out));
        }
    }

    private void getVerify() {
        if (ArmsUtils.isEmpty(userNameET.getText().toString())) {
            showMessage("请输入手机号码");
            return;
        }

        if (!ArmsUtils.isPhoneNum(userNameET.getText().toString())) {
            showMessage("手机号码格式不正确");
            return;
        }
        if (time == time_limit || time <= 0) {
            time = time_limit - 1;
            initTimer();
            timer.schedule(timerTask, 0, 1000);
            getCache().put("mobile", userNameET.getText().toString());
            mPresenter.getVerify();
        }
    }

    private void register() {
        if (ArmsUtils.isEmpty(userNameET.getText().toString())) {
            showMessage("请输入手机号码");
            return;
        }

        if (!ArmsUtils.isPhoneNum(userNameET.getText().toString())) {
            showMessage("手机号码格式不正确");
            return;
        }

        if (ArmsUtils.isEmpty(validateET.getText().toString())) {
            showMessage("请输入验证码");
            return;
        }

        if (validateET.getText().toString().length() != 4) {
            showMessage("验证码格式不正确");
            return;
        }
        getCache().put("mobile", userNameET.getText().toString());
        getCache().put("verifyCode", validateET.getText().toString());
        mPresenter.register();
    }


    private void registerLast() {
        if (ArmsUtils.isEmpty(nameET.getText().toString())) {
            showMessage("请输入会员号");
            return;
        }

        if (ArmsUtils.isEmpty(birthTV.getText().toString())) {
            showMessage("请选择出生年月");
            return;
        }

        if (!ArmsUtils.isEmpty((String) getCache().get("type"))) {
            if (ArmsUtils.isEmpty(inviteET.getText().toString())) {
                showMessage("请输入邀请信息");
                return;
            }
        }
        getCache().put("nickName", nameET.getText().toString());
        getCache().put("code", inviteET.getText().toString());
        mPresenter.registerLast();

    }

    private void initTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                getValidateV.post(new Runnable() {
                    @Override
                    public void run() {
                        if (time <= 0 && timer != null) {
                            timer.cancel();
                            timer = null;
                            timerTask.cancel();
                            timerTask = null;
                            getValidateV.setText("重新获取");
                        } else {
                            getValidateV.setText(time + "s");
                        }
                        time--;
                    }
                });
            }
        };

        timer = new Timer();
    }

    @Override
    public void showVerity() {
        time = 0;
    }

    @Override
    public void hasRegister(boolean register) {
        stepOneContentV.setVisibility(register ? View.GONE : View.VISIBLE);
        infoLayoutV.setVisibility(register ? View.VISIBLE : View.GONE);
        infoTV.setText("手机号已被注册，请换号重新注册！");
        infoBTNV.setText("重新注册");
    }

    @Override
    public void inputUserInfo() {
        stepOneContentV.setVisibility(View.GONE);
        stepTwoContentV.setVisibility(View.VISIBLE);
        stepNameLayoutV.setBackgroundResource(R.drawable.register_current_step);
        stepNameV.setBackgroundResource(R.mipmap.reg_name_finished);
    }

    private void goBack() {
        time = 0;
        getValidateV.setText("获取验证码");
        stepOneContentV.setVisibility(View.VISIBLE);
        stepTwoContentV.setVisibility(View.GONE);
        stepNameLayoutV.setBackgroundResource(R.drawable.register_last_step);
        stepNameV.setBackgroundResource(R.mipmap.reg_name_unfinished);
    }

    @Override
    public void registerSuccess() {
        stepTwoContentV.setVisibility(View.GONE);
        stepLastLayoutV.setBackgroundResource(R.drawable.register_current_step);
        stepLastV.setBackgroundResource(R.mipmap.reg_finished);
        infoLayoutV.setVisibility(View.VISIBLE);
        infoTV.setText("恭喜您，注册成功，可进行下一步！");
        infoBTNV.setText("去下单");
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Cache getCache() {
        return provideCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }
}
