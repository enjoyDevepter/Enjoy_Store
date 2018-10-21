package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerHospitalInfoComponent;
import cn.ehanmy.hospital.di.module.HospitalInfoModule;
import cn.ehanmy.hospital.mvp.contract.HospitalInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.store.StoreBean;
import cn.ehanmy.hospital.mvp.presenter.HospitalInfoPresenter;
import cn.ehanmy.hospital.mvp.ui.widget.CustomDialog;
import cn.ehanmy.hospital.util.EdittextUtil;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HospitalInfoActivity extends BaseActivity<HospitalInfoPresenter> implements HospitalInfoContract.View, View.OnClickListener {

    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.modify_image)
    View imageV;
    @BindView(R.id.image)
    ImageView imageIV;
    @BindView(R.id.name)
    TextView nameTV;
    @BindView(R.id.phone)
    TextView phoneTV;
    @BindView(R.id.time)
    TextView timeTV;  // 早9:00-晚18:00
    @BindView(R.id.address)
    TextView addressTV;
    @BindView(R.id.modify_info)
    View infoV;
    @Inject
    ImageLoader mImageLoader;

    StoreBean storeBean;

    CustomDialog dialog = null;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHospitalInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .hospitalInfoModule(new HospitalInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_hospital_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleTV.setText("店铺信息");
        backV.setOnClickListener(this);
        imageV.setOnClickListener(this);
        infoV.setOnClickListener(this);
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
    public Cache getCache() {
        return provideCache();
    }

    @Override
    public void updateUI(StoreBean storeBean) {
        this.storeBean = storeBean;
        nameTV.setText(storeBean.getName());
        phoneTV.setText("联系电话：" + storeBean.getTellphone());
        timeTV.setText("营业时间：早" + storeBean.getStarTime() + "-晚" + storeBean.getEndTime());
        addressTV.setText(storeBean.getAddress());

        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(storeBean.getImage())
                        .placeholder(R.drawable.place_holder_img)
                        .imageView(imageIV)
                        .build());

    }

    @Override
    public void changeOk() {
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }

        showMessage("医院信息修改成功");
    }


    private void showChangeDialog() {
        dialog = CustomDialog.create(getSupportFragmentManager())
                .setViewListener(new CustomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        ((TextView) view.findViewById(R.id.name)).setText("本店名称:" + storeBean.getName());
                        ((TextView) view.findViewById(R.id.address)).setText("地址:" + storeBean.getAddress());
                        EditText phone = view.findViewById(R.id.phone);
                        phone.setText(storeBean.getTellphone());
                        EditText startTime = view.findViewById(R.id.start_time);
                        startTime.setText(storeBean.getStarTime());
                        EditText endTime = view.findViewById(R.id.end_time);
                        endTime.setText(storeBean.getEndTime());
//                        startTime.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                new TimePickerView.Builder(HospitalInfoActivity.this, new TimePickerView.OnTimeSelectListener() {
//
//                                    @Override
//                                    public void onTimeSelect(Date date, View v) {
//                                        startTime.setText(date.getHours() + ":" + date.getMinutes());
//                                        showMessage(date.getHours() + ":" + date.getMinutes());
//                                    }
//                                })
//                                        .isDialog(true)
//                                        .setType(new boolean[]{false, false, false, true, true, false})
//                                        .build().show();
//
//                            }
//                        });
//                        endTime.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                new TimePickerView.Builder(HospitalInfoActivity.this, new TimePickerView.OnTimeSelectListener() {
//
//                                    @Override
//                                    public void onTimeSelect(Date date, View v) {
//                                        endTime.setText(date.getHours() + ":" + date.getMinutes());
//                                        showMessage(date.getHours() + ":" + date.getMinutes());
//                                    }
//                                })
//                                        .isDialog(true)
//                                        .setType(new boolean[]{false, false, false, true, true, false})
//                                        .build().show();
//                            }
//                        });
                        view.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ArmsUtils.isEmpty(phone.getText().toString())) {
                                    showMessage("请输入联系电话");
                                    return;
                                }
                                if (EdittextUtil.isEmpty(startTime)) {
                                    showMessage("请输入开始营业时间");
                                    return;
                                }
                                if (EdittextUtil.isEmpty(endTime)) {
                                    showMessage("请输入结束营业时间");
                                    return;
                                }
                                String startTimeText = startTime.getText()+"";
                                String endTimeText = endTime.getText()+"";
                                String reg = "0[0-9]:[0-5][0-9]|1[0-9]:[0-5][0-9]|2[0-3]:[0-5][0-9]";  // 00:00
                                Pattern pattern = Pattern.compile(reg);
                                Matcher matcherStart = pattern.matcher(startTimeText);
                                boolean startRight = matcherStart.matches();
                                if(!startRight){
                                    showMessage("开始时间输入格式错误，请输入00:00格式");
                                    return;
                                }
                                Matcher matcherEnd = pattern.matcher(endTimeText);
                                boolean endRight = matcherEnd.matches();
                                if(!endRight){
                                    showMessage("结束时间输入格式错误，请输入00:00格式");
                                    return;
                                }
                                provideCache().put("tellphone", phone.getText().toString());
                                mPresenter.changeHospitalInfo(phone.getText()+"",startTimeText,endTimeText);
                            }
                        });

                    }
                })
                .setLayoutRes(R.layout.dialog_change_hospital_info)
                .setWidth(ArmsUtils.getDimens(this, R.dimen.modify_hospital_width))
                .setHeight(ArmsUtils.getDimens(this, R.dimen.modify_hospital_height))
                .setDimAmount(0.5f)
                .isCenter(true)
                .show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
                break;
            case R.id.modify_image:
                ArmsUtils.startActivity(ChangeHospitalImageActivity.class);
                break;
            case R.id.modify_info:
                showChangeDialog();
                break;
        }
    }
}
