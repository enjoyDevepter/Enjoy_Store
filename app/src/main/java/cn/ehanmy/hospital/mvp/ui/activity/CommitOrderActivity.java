package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.AppManager;
import com.jess.arms.utils.ArmsUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerCommitOrderComponent;
import cn.ehanmy.hospital.di.module.CommitOrderModule;
import cn.ehanmy.hospital.mvp.contract.CommitOrderContract;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.PayEntry;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.order.GoPayResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyResponse;
import cn.ehanmy.hospital.mvp.presenter.CommitOrderPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.PayItemAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.CustomDialog;
import cn.ehanmy.hospital.mvp.ui.widget.MoneyView;
import cn.ehanmy.hospital.mvp.ui.widget.ShapeImageView;
import cn.ehanmy.hospital.util.CacheUtil;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 显示订单信息并提供支付入口（支付二维码）
 */
public class CommitOrderActivity extends BaseActivity<CommitOrderPresenter> implements CommitOrderContract.View{


    public static final String PAY_WEIXIN = "WEIXIN_QRCODE";
    public static final String PAY_ZHIFUBAO = "ALIPAY_QRCODE";


    public static final String KEY_FOR_ORDER_BEAN = "KEY_FOR_ORDER_BEAN";
    public static final String KEY_FOR_GO_IN_TYPE = "key_for_go_in_type";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static final int GO_IN_TYPE_CONFIRM = 1;  // 从订单详情进入
    public static final int GO_IN_TYPE_ORDER_LIST = 2;  // 从订单列表进入

    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.head_image)
    ShapeImageView head_image;  // 用户头像
    @BindView(R.id.image)
    ImageView image;  // 项目图片
    @BindView(R.id.order_name)
    TextView order_name;  // 项目名称
    @BindView(R.id.price)
    MoneyView priceMV;  // 订单金额
    @BindView(R.id.name)
    TextView name;  // 用户姓名
    @BindView(R.id.phone)
    TextView phone;  // 用户手机
    @BindView(R.id.hospital)
    TextView hospital;  // 用户医院
    @BindView(R.id.member_code)
    TextView member_code;  // 会员名
    @BindView(R.id.addr)
    TextView addr;
    @BindView(R.id.pay)
    View payV;

    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager appManager;
    private CustomDialog payOkDialog;
    private CustomDialog confirmPayDialog;
    private CustomDialog payErrorDialog;

    @BindView(R.id.pay_item)
    RadioGroup pay_item;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommitOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .commitOrderModule(new CommitOrderModule(this))
                .build()
                .inject(this);
    }


    public void updatePayEntry(List<PayEntry> payEntries){
        pay_item.removeAllViews();
        RadioButton rb = null;
        LayoutInflater from = LayoutInflater.from(this);
        for(int i = 0;i<payEntries.size();i++){
            PayEntry payEntry = payEntries.get(i);
            RadioButton v = (RadioButton) from.inflate(R.layout.pay_list_item,null);
            v.setText(payEntry.getName());
            v.setId(i);
            v.setTag(payEntry);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,72);
            pay_item.addView(v,params);
            if(i == 0){
                rb = v;
            }
        }
        pay_item.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View viewById = group.findViewById(checkedId);
                CommitOrderActivity.this.payEntry = (PayEntry) viewById.getTag();

                String payId = payEntry.getPayId();
                if(cacheView != null){
                    group.removeView(cacheView);
                    cacheView = null;
                }
                if(PAY_WEIXIN.equals(payId) || PAY_ZHIFUBAO.equals(payId)){
                    int index = group.indexOfChild(viewById);
                    View root = LayoutInflater.from(CommitOrderActivity.this).inflate(R.layout.commit_pay_rq_item,null);
                    cacheView = root;
                    ImageView imageView = root.findViewById(R.id.image);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    root.setLayoutParams(params);
                    group.addView(root,index+1);
                    mPresenter.orderPay(payId,money,orderId,imageView);
                }
            }
        });
        if(rb != null){
            rb.setChecked(true);
        }
    }

    private View cacheView;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_commit_order; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public void updateMember(MemberBean memberBean){
        if(memberBean == null){
            return;
        }
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .placeholder(R.drawable.place_holder_img)
                        .url(memberBean.getHeadImage())
                        .placeholder(R.drawable.place_holder_img)
                        .imageView(head_image)
                        .build());
        name.setText(memberBean.getRealName());
        phone.setText(memberBean.getMobile());
        member_code.setText(memberBean.getUserName());

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title, this, "提交订单");
//        HospitaInfoBean storeBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO);
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        updateMember(memberBean);
//        hospital.setText(storeBean.getName());
//        addr.setText(storeBean.getAddress());

    }


    private String orderId;
    private long money;
    private PayEntry payEntry;

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
    public void onBackPressed() {
        if (null != payOkDialog && !payOkDialog.isHidden()) {
            payOkDialog.dismiss();
            appManager.killAllBeforeClass(BuyCenterActivity.class);
            return;
        }
        super.onBackPressed();
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
    public void showPaySuccess(GoPayResponse response, OrderBean orderBean) {
        GoodsOrderBean goodsOrderBean = orderBean.getGoodsList().get(0);
        updateView(goodsOrderBean.getImage(),goodsOrderBean.getName()
        ,response.getPayMoney(),response.getPayStatus(),response.getOrderId(),response.getOrderTime());
    }

    public void showPaySuccess(GoodsBuyResponse response) {
        cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsOrderBean goods = response.getGoods();
        updateView(goods.getImage(), goods.getName(),response.getPayMoney(),
                response.getPayStatus(),response.getOrderId(),response.getOrderTime());
    }

    private void updateView(String imageUrl,String orderName,long payMoney,
                            String payStatus,String orderId,long orderTime){
        this.orderId = orderId;
        this.money = payMoney;
        payV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payEntry == null){
                    showMessage("请选择付款方式");
                    return;
                }
                String payId = CommitOrderActivity.this.payEntry.getPayId();
                if(payId.startsWith("OFFLINE_")){
                    confirmPay();
                }else if(PAY_WEIXIN.equals(payId) || PAY_ZHIFUBAO.equals(payId)){
                    mPresenter.getPayStatus(orderId);
                }
            }
        });
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .placeholder(R.drawable.place_holder_img)
                        .url(imageUrl)
                        .imageView(image)
                        .build());
        order_name.setText(orderName);
        priceMV.setMoneyText(ArmsUtils.formatLong(payMoney));
        if ("1".equals(payStatus)) {
            payOk(orderId, orderTime);
        }
    }


    private void confirmPay(){
        confirmPayDialog = CustomDialog.create(getSupportFragmentManager())
                .setViewListener(new CustomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.orderPay(payEntry.getPayId(),money,orderId);
                            }
                        });
                        view.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confirmPayDialog.dismiss();
                            }
                        });
                    }
                })
                .setLayoutRes(R.layout.confirm_pay_dialog_layout)
                .setDimAmount(0.5f)
                .isCenter(true)
                .setCancelOutside(false)
                .setWidth(ArmsUtils.dip2px(CommitOrderActivity.this, 228))
                .show();
    }


    public void payOk(String orderId, long orderTime) {
        if(confirmPayDialog != null){
            confirmPayDialog.dismiss();
            confirmPayDialog = null;
        }
        payOkDialog = CustomDialog.create(getSupportFragmentManager())
                .setViewListener(new CustomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        ((TextView) view.findViewById(R.id.project_name)).setText(order_name.getText());
                        ((TextView) view.findViewById(R.id.project_id)).setText(orderId);
                        ((TextView) view.findViewById(R.id.project_leader)).setText(name.getText());
                        ((TextView) view.findViewById(R.id.project_time)).setText(sdf.format(orderTime));
                        view.findViewById(R.id.project).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                payOkDialog.dismiss();
                                Intent intent = new Intent(CommitOrderActivity.this, OrderInfoActivity.class);
                                intent.putExtra(OrderInfoActivity.KEY_FOR_ORDER_ID, orderId);
                                ArmsUtils.startActivity(intent);
                                appManager.killAllBeforeClass(BuyCenterActivity.class);
                            }
                        });
                        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                payOkDialog.dismiss();
                                int type = getIntent().getIntExtra(CommitOrderActivity.KEY_FOR_GO_IN_TYPE,0);
                                switch (type){
                                    case CommitOrderActivity.GO_IN_TYPE_CONFIRM:
                                        ArmsUtils.startActivity(GoodsListActivity.class);
                                        break;
                                    case CommitOrderActivity.GO_IN_TYPE_ORDER_LIST:
                                        killMyself();
                                        break;
                                }
                            }
                        });
                    }
                })
                .setLayoutRes(R.layout.pay_ok_dialog_layout)
                .setDimAmount(0.5f)
                .isCenter(true)
                .setCancelOutside(false)
                .setWidth(ArmsUtils.dip2px(CommitOrderActivity.this, 228))
                .show();
    }


    public void showPayError(String errorInfo){
        payErrorDialog = CustomDialog.create(getSupportFragmentManager())
                .setViewListener(new CustomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        TextView content = view.findViewById(R.id.content);
                        content.setText(errorInfo);
                        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                payErrorDialog.dismiss();
                                payErrorDialog = null;
                            }
                        });
                    }
                })
                .setLayoutRes(R.layout.pay_error_dialog)
                .setDimAmount(0.5f)
                .isCenter(true)
                .setCancelOutside(false)
                .setWidth(456)
                .show();
    }
}
