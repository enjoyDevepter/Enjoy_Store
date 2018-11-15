package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.app.EventBusTags;
import cn.ehanmy.hospital.di.component.DaggerHGoodsConfirmComponent;
import cn.ehanmy.hospital.di.module.HGoodsConfirmModule;
import cn.ehanmy.hospital.mvp.contract.HGoodsConfirmContract;
import cn.ehanmy.hospital.mvp.model.entity.Hospital;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Goods;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsSpecValueBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationTimeBean;
import cn.ehanmy.hospital.mvp.presenter.HGoodsConfirmPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.SpecLabelTextProvider;
import cn.ehanmy.hospital.mvp.ui.widget.CustomDialog;
import cn.ehanmy.hospital.mvp.ui.widget.LabelsView;
import cn.ehanmy.hospital.mvp.ui.widget.MoneyView;
import cn.ehanmy.hospital.util.CacheUtil;

import static com.jess.arms.utils.ArmsUtils.getContext;
import static com.jess.arms.utils.Preconditions.checkNotNull;


public class HGoodsConfirmActivity extends BaseActivity<HGoodsConfirmPresenter> implements HGoodsConfirmContract.View, View.OnClickListener, LabelsView.OnLabelSelectChangeListener {

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
    @BindView(R.id.spec)
    View specV;
    @BindView(R.id.goods_spec)
    TextView goodSpecTV;
    @BindView(R.id.mask_spec)
    View maskSpecV;
    @BindView(R.id.spec_close)
    View closeV;
    @BindView(R.id.spec_layout)
    View specLayoutV;
    @BindView(R.id.spec_image)
    ImageView spceImageIV;
    @BindView(R.id.spec_name)
    TextView spceNameTV;
    @BindView(R.id.spec_price)
    MoneyView spcePriceTV;
    @BindView(R.id.spec_goods_id)
    TextView spceIDTV;
    @BindView(R.id.specs)
    LabelsView speceLabelsView;
    @Inject
    ImageLoader mImageLoader;
    GoodsConfirmResponse response;

    private CustomDialog payOkDialog;

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
        specV.setOnClickListener(this);
        confirmV.setOnClickListener(this);
        hospitalV.setOnClickListener(this);
        timeV.setOnClickListener(this);
        maskSpecV.setOnClickListener(this);
        closeV.setOnClickListener(this);
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
                Intent hospitalIntent = new Intent(this, ChoiceHospitalActivity.class);
                hospitalIntent.putExtra("goodsId", response.getGoods().getGoodsId());
                hospitalIntent.putExtra("merchId", response.getGoods().getMerchId());
                ArmsUtils.startActivity(hospitalIntent);
                break;
            case R.id.time_layout:
                Intent intent = new Intent(this, ChoiceTimeActivity.class);
                intent.putExtra("from", "hAppointment");
                intent.putExtra("goodsId", response.getGoods().getGoodsId());
                intent.putExtra("merchId", response.getGoods().getMerchId());
                ArmsUtils.startActivity(intent);
                break;
            case R.id.confirm:
                if (ArmsUtils.isEmpty((String) provideCache().get("hospital_id"))) {
                    showMessage("请选择医院");
                    return;
                }
                if (ArmsUtils.isEmpty((String) provideCache().get("reservationTime"))) {
                    showMessage("请选择预约时间");
                    return;
                }
                mPresenter.makeAppointment();
                break;
            case R.id.spec:
            case R.id.mask_spec:
            case R.id.spec_close:
                showSpec();
                break;
        }
    }

    public void payOk() {
        payOkDialog = CustomDialog.create(getSupportFragmentManager())
                .setViewListener(new CustomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArmsUtils.startActivity(HGoodsListActivity.class);
                                killMyself();
                                payOkDialog.dismiss();
                            }
                        });
                    }
                })
                .setLayoutRes(R.layout.appointment_ok_dialog_layout)
                .setDimAmount(0.5f)
                .isCenter(true)
                .setCancelOutside(false)
                .setWidth(ArmsUtils.dip2px(this, 228))
                .show();
    }

    @Subscriber(tag = EventBusTags.HOSPITAL_CHANGE_EVENT)
    private void updateHospitalInfo(Hospital hospital) {
        provideCache().put("hospital_name", hospital.getName());
        provideCache().put("hospital_id", hospital.getHospitalId());
        hosptialTV.setText(hospital.getName());
        provideCache().put("baseInfoBean", hospital);
    }


    @Subscriber(tag = EventBusTags.CHANGE_APPOINTMENT_TIME)
    private void updateAppointmentInfo(ReservationDateBean reservationDateBean) {
        String reservationTime = "";
        for (ReservationTimeBean time : reservationDateBean.getReservationTimeList()) {
            if (time.isChoose()) {
                reservationTime = time.getTime();
                break;
            }
        }
        timeTV.setText(reservationDateBean.getDate() + "   " + reservationTime);
        provideCache().put("reservationDate", reservationDateBean.getDate());
        provideCache().put("reservationTime", reservationTime);
    }

    private void showSpec() {
        if (null == speceLabelsView.getLabels()
                || (null != speceLabelsView.getLabels() && speceLabelsView.getLabels().size() <= 0)) {
            return;
        }
        if (!maskSpecV.isShown()) {
            maskSpecV.setVisibility(View.VISIBLE);
            maskSpecV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_in));
            specLayoutV.setVisibility(View.VISIBLE);
            specLayoutV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.for_butom_in));
            spcePriceTV.setMoneyText(String.valueOf(response.getGoods().getSalePrice()));
            spceIDTV.setText(response.getGoods().getCode());
            spceNameTV.setText(response.getGoods().getName());
            mImageLoader.loadImage(spceImageIV.getContext(),
                    ImageConfigImpl
                            .builder()
                            .placeholder(R.drawable.place_holder_img)
                            .url(response.getGoods().getImage())
                            .imageView(spceImageIV)
                            .build());

        } else {
            maskSpecV.setVisibility(View.GONE);
            maskSpecV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_out));
            specLayoutV.setVisibility(View.GONE);
            specLayoutV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.for_buttom_out));
        }
    }

    @Override
    public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
        if (isSelect) {
            GoodsSpecValueBean goodsSpecValue = (GoodsSpecValueBean) data;
            provideCache().put("specValueId", goodsSpecValue.getSpecValueId());
            provideCache().put("merchId", response.getGoods().getMerchId());
            mPresenter.getGoodsDetails();
        }
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
    public void updateUI(GoodsConfirmResponse response) {
        if (null == this.response ||
                !this.response.getGoodsSpecValueList().equals(response.getGoodsSpecValueList())) {
            speceLabelsView.setOnLabelSelectChangeListener(null);
            speceLabelsView.setLabels(response.getGoodsSpecValueList(), new SpecLabelTextProvider());
            provideCache().put("specValueId", response.getGoods().getGoodsSpecValue().getSpecValueId());
            String specValueId = (String) provideCache().get("specValueId");
            if (!ArmsUtils.isEmpty(specValueId)) {
                for (int i = 0; i < response.getGoodsSpecValueList().size(); i++) {
                    if (response.getGoodsSpecValueList().get(i).getSpecValueId().equals(specValueId)) {
                        speceLabelsView.setSelects(i);
                        break;
                    }
                }
            }
            speceLabelsView.setOnLabelSelectChangeListener(this);
        }
        this.response = response;
        Goods goods = response.getGoods();

        if (null != goods) {
            mImageLoader.loadImage(this,
                    ImageConfigImpl
                            .builder()
                            .placeholder(R.drawable.place_holder_img)
                            .url(goods.getImage())
                            .isCenterCrop(true)
                            .imageView(imageIV)
                            .build());

            nameTV.setText(goods.getName());
            priceMV.setMoneyText(String.valueOf(goods.getSalePrice()));
            goodSpecTV.setText(goods.getGoodsSpecValue().getSpecValueName());
        }
        spceIDTV.setText(goods.getCode());
        spceNameTV.setText(goods.getName());
        spcePriceTV.setMoneyText(String.valueOf(goods.getSalePrice()));
    }
}
