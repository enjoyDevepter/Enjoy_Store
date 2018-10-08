package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerShopAppointmentInfoComponent;
import cn.ehanmy.hospital.di.module.ShopAppointmentInfoModule;
import cn.ehanmy.hospital.mvp.contract.ShopAppointmentInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.UserAppointmentGoodsBean;
import cn.ehanmy.hospital.mvp.presenter.ShopAppointmentInfoPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ShopAppointmentInfoActivity extends BaseActivity<ShopAppointmentInfoPresenter> implements ShopAppointmentInfoContract.View {

    public static final String KEY_FOR_APPOINTMENT_ID = "key_for_appointment_id";

    @BindView(R.id.title_Layout)
    View title;

    @BindView(R.id.related)
    View related;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.appointment_order)
    TextView form_id;
    @BindView(R.id.appointment_state)
    TextView form_state;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.form_tel)
    TextView form_tel;
    @BindView(R.id.form_add)
    TextView form_add;
    @BindView(R.id.image_view)
    ImageView image_view;
    @BindView(R.id.project_name)
    TextView project_name;
    @BindView(R.id.order_time)
    TextView order_time;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShopAppointmentInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopAppointmentInfoModule(new ShopAppointmentInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shop_appointment_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"预约详细");

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

    public void updateOrderInfo(OrderProjectDetailBean orderInfoBean){
        UserAppointmentGoodsBean goodsOrderBean = orderInfoBean.getGoods();
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .placeholder(R.drawable.place_holder_img)
                        .url(goodsOrderBean.getImage())
                        .imageView(image_view)
                        .build());

        related.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopAppointmentInfoActivity.this,RelatedListActivity.class);
                intent.putExtra(RelatedListActivity.KEY_FOR_MEMBER_ID,orderInfoBean.getMember().getMemberId());
                intent.putExtra(RelatedListActivity.KEY_FOR_RESERVATION_ID,orderInfoBean.getReservationId());
                launchActivity(intent);
            }
        });

        form_id.setText(orderInfoBean.getReservationId());
        form_state.setText(orderInfoBean.getStatusDesc());
        time.setText(orderInfoBean.getCreateDate());
        name.setText(orderInfoBean.getMember().getMemberId());
        form_tel.setText(orderInfoBean.getMember().getMobile());
        form_add.setText(orderInfoBean.getAddress());
        project_name.setText(goodsOrderBean.getName());
        order_time.setText(orderInfoBean.getReservationTime());
    }


    public Activity getActivity(){
        return this;
    }
}
