package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import cn.ehanmy.hospital.di.component.DaggerOrderConfirmComponent;
import cn.ehanmy.hospital.di.module.OrderConfirmModule;
import cn.ehanmy.hospital.mvp.contract.OrderConfirmContract;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Goods;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsSpecValueBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.mvp.model.entity.store.StoreBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationTimeBean;
import cn.ehanmy.hospital.mvp.presenter.OrderConfirmPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.SpecLabelTextProvider;
import cn.ehanmy.hospital.mvp.ui.widget.LabelsView;
import cn.ehanmy.hospital.mvp.ui.widget.MoneyView;
import cn.ehanmy.hospital.util.CacheUtil;

import static com.jess.arms.utils.ArmsUtils.getContext;
import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 确认订单页面，支付页面前通过这个页面确定订单
 */
public class OrderConfirmActivity extends BaseActivity<OrderConfirmPresenter> implements OrderConfirmContract.View, View.OnClickListener, LabelsView.OnLabelSelectChangeListener, View.OnFocusChangeListener, TextView.OnEditorActionListener {
    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.member_code)
    TextView memberCodeTV;
    @BindView(R.id.hospital_layout)
    View hospitalV;
    @BindView(R.id.store)
    TextView storeTV;
    @BindView(R.id.remark)
    EditText remarkET;
    @BindView(R.id.image)
    ImageView imageIV;
    @BindView(R.id.name)
    TextView nameTV;
    @BindView(R.id.price)
    MoneyView priceMV;
    @BindView(R.id.goods_spec)
    TextView goodSpecTV;
    @BindView(R.id.balance)
    TextView balanceTV;
    @BindView(R.id.money)
    EditText moneyET;
    @BindView(R.id.payPrice)
    MoneyView payPriceTV;
    @BindView(R.id.payMoney)
    MoneyView payMoneyTV;
    @BindView(R.id.confirm)
    View confirmV;
    @BindView(R.id.time_layout)
    View timeV;
    @BindView(R.id.time)
    TextView timeTV;
    @BindView(R.id.spec)
    View specV;
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
    @BindView(R.id.pay)
    MoneyView payMV;
    @Inject
    ImageLoader mImageLoader;
    GoodsConfirmResponse response;
    private volatile boolean shouldSubmit;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderConfirmComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderConfirmModule(new OrderConfirmModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_confirm; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleTV.setText("确认订单");
        backV.setOnClickListener(this);
        specV.setOnClickListener(this);
        timeV.setOnClickListener(this);
        confirmV.setOnClickListener(this);
        hospitalV.setOnClickListener(this);
        maskSpecV.setOnClickListener(this);
        closeV.setOnClickListener(this);
        moneyET.setOnFocusChangeListener(this);
        moneyET.setOnEditorActionListener(this);
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        memberCodeTV.setText(memberBean.getUserName());
        StoreBean storeBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_STORE_INFO);
        storeTV.setText(storeBean.getName());
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
        moneyET.setHint(ArmsUtils.formatLong(response.getMoney()));
        balanceTV.setText(ArmsUtils.formatLong(response.getBalance()));
        payPriceTV.setMoneyText(String.valueOf(goods.getSalePrice()));
        payMoneyTV.setMoneyText(ArmsUtils.formatLong(response.getMoney()));
        payMV.setMoneyText(ArmsUtils.formatLong(response.getPayMoney()));
        spceIDTV.setText(goods.getCode());
        spceNameTV.setText(goods.getName());
        spcePriceTV.setMoneyText(String.valueOf(goods.getSalePrice()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
                break;
            case R.id.spec:
            case R.id.mask_spec:
            case R.id.spec_close:
                showSpec();
                break;
            case R.id.confirm:
                getCache().put("remark", remarkET.getText() + "");
                mPresenter.placeGoodsOrder();
                break;
            case R.id.time_layout:
                Intent intent = new Intent(this, ChoiceTimeActivity.class);
                intent.putExtra("from", "placeOrder");
                ArmsUtils.startActivity(intent);
                break;
        }
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
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            shouldSubmit = hasFocus;
        } else {
            if (shouldSubmit) {
                String m = moneyET.getText().toString();
                moneyET.setText("");
                if (ArmsUtils.isEmpty(m)) {
                    return;
                }
                provideCache().put("money", Long.valueOf(m) * 100);
                shouldSubmit = hasFocus;
                mPresenter.getGoodsDetails();
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                //使EditText触发一次失去焦点事件
                v.setFocusable(false);
//                v.setFocusable(true); //这里不需要是因为下面一句代码会同时实现这个功能
                v.setFocusableInTouchMode(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            //隐藏软键盘
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            remarkET.requestFocus();
            return true;
        }
        return false;
    }
}
