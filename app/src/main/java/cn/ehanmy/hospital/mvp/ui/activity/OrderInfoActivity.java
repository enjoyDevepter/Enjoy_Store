package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerOrderInfoComponent;
import cn.ehanmy.hospital.di.module.OrderInfoModule;
import cn.ehanmy.hospital.mvp.contract.OrderInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderMemberInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderRecipientInfoBean;
import cn.ehanmy.hospital.mvp.presenter.OrderInfoPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 订单详情页面
 */
public class OrderInfoActivity extends BaseActivity<OrderInfoPresenter> implements OrderInfoContract.View {

    public static final String KEY_FOR_ORDER_ID = "key_for_order_id";
    @Inject
    ImageLoader mImageLoader;
    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.form_id)
    TextView form_id;
    @BindView(R.id.form_state)
    TextView form_state;
    @BindView(R.id.form_remark)
    TextView form_remark;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.form_tel)
    TextView form_tel;
    @BindView(R.id.form_add)
    TextView form_add;
    @BindView(R.id.skill)
    TextView skill;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.project_name)
    TextView project_name;
    @BindView(R.id.order_time)
    TextView order_time;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BindView(R.id.perple_info_parent)
    View perple_info_parent;

    public void updateOrderInfo(OrderInfoBean orderInfoBean) {
        GoodsOrderBean goodsOrderBean = orderInfoBean.getGoodsList().get(0);
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .placeholder(R.drawable.place_holder_img)
                        .url(goodsOrderBean.getImage())
                        .imageView(image)
                        .build());

        form_id.setText(orderInfoBean.getOrderId());
        form_state.setText(orderInfoBean.getOrderStatusDesc());
        form_remark.setText(orderInfoBean.getRemark());
        time.setText(simpleDateFormat.format(new Date(orderInfoBean.getOrderTime())));
        OrderRecipientInfoBean orderRecipientInfo = orderInfoBean.getOrderRecipientInfo();
        if (orderRecipientInfo != null) {
            perple_info_parent.setVisibility(View.VISIBLE);
            form_tel.setText(orderRecipientInfo.getMobile());
            form_add.setText(orderRecipientInfo.getAddress());
            String nameStr = orderRecipientInfo.getRealName();
            if(TextUtils.isEmpty(nameStr)){
                nameStr = orderRecipientInfo.getMobile();
            }
            name.setText(nameStr);
        }else{
            perple_info_parent.setVisibility(View.GONE);
            OrderMemberInfoBean member = orderInfoBean.getMember();
            name.setText(member.getMobile());
        }
        skill.setText(goodsOrderBean.getGoodsSpecValue().getSpecValueName());
        project_name.setText(goodsOrderBean.getName());
        if (!ArmsUtils.isEmpty(orderInfoBean.getAppointmentsDate())) {
            order_time.setText("预约时间：" + orderInfoBean.getAppointmentsDate() + "  " + orderInfoBean.getAppointmentsTime());
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderInfoModule(new OrderInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title, this, "订单详情");
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

    public Activity getActivity() {
        return this;
    }
}
