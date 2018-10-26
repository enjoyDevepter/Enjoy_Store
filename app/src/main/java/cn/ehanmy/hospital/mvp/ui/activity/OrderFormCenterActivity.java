package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerOrderFormCenterComponent;
import cn.ehanmy.hospital.di.module.OrderFormCenterModule;
import cn.ehanmy.hospital.mvp.contract.OrderFormCenterContract;
import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoBean;
import cn.ehanmy.hospital.mvp.presenter.OrderFormCenterPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.HeightItemDecoration;
import cn.ehanmy.hospital.mvp.ui.adapter.OrderCenterListAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.CustomDialog;
import cn.ehanmy.hospital.mvp.ui.widget.MoneyView;
import cn.ehanmy.hospital.util.EdittextUtil;
import io.rx_cache2.internal.Disk;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 订单中心页面
 */
public class OrderFormCenterActivity extends BaseActivity<OrderFormCenterPresenter> implements OrderFormCenterContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, TabLayout.OnTabSelectedListener, OrderCenterListAdapter.OnChildItemClickLinstener {

    @BindView(R.id.title_Layout)
    View title_Layout;
    @BindView(R.id.search_layout)
    View search_layout;
    @BindView(R.id.code)
    View code;
    @BindView(R.id.search_btn)
    View search;
    @BindView(R.id.clear_btn)
    View clear;
    @BindView(R.id.search_key)
    EditText searchKey;  // provideCache().put("tellphone", phone.getText().toString());
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.contentList)
    RecyclerView contentList;
    @BindView(R.id.no_date)
    View noDataV;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    OrderCenterListAdapter mAdapter;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderFormCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderFormCenterModule(new OrderFormCenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_form_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        code.setVisibility(View.GONE);
        search.setOnClickListener(this);
        clear.setOnClickListener(this);
        new TitleUtil(title_Layout, this, "订单中心");
        tabLayout.addTab(tabLayout.newTab().setTag("1").setText("未支付"));
        tabLayout.addTab(tabLayout.newTab().setTag("31").setText("可消费"));
        tabLayout.addTab(tabLayout.newTab().setTag("5").setText("已完成"));
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        tabLayout.addOnTabSelectedListener(this);
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tablayout_divider_vertical));
        ArmsUtils.configRecyclerView(contentList, mLayoutManager);
        contentList.addItemDecoration(new HeightItemDecoration(8));
        contentList.setAdapter(mAdapter);
        mAdapter.setOnChildItemClickLinstener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        initPaginate();
        provideCache().put("type", "1");
        mPresenter.getOrderList(true);

        searchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                provideCache().put("key",s+"");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    @Override
    public Cache getCache() {
        return provideCache();
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(contentList);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mPaginate = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                String s = searchKey.getText().toString();
                if (ArmsUtils.isEmpty(s)) {
                    showMessage("请输入搜索关键字后重试");
                    return;
                }
                mPresenter.getOrderList(true);
                break;
            case R.id.clear_btn:
                searchKey.setText("");
                provideCache().put("key",null);
                mPresenter.getOrderList(true);
                break;
        }
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public void showError(boolean hasDate) {
        contentList.setVisibility(hasDate ? View.VISIBLE : View.INVISIBLE);
        noDataV.setVisibility(hasDate ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void setLoadedAllItems(boolean has) {
        this.hasLoadedAllItems = has;
    }


    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.getOrderList(false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return hasLoadedAllItems;
                }
            };

            mPaginate = Paginate.with(contentList, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getOrderList(true);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                provideCache().put("type", "1");
                break;
            case 1:
                provideCache().put("type", "31");
                break;
            case 2:
                provideCache().put("type", "5");
                break;
            case 3:
                provideCache().put("type", "");
                break;
        }
        mPresenter.getOrderList(true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onChildItemClick(View v, OrderCenterListAdapter.ViewName viewname, int position) {
        switch (viewname) {
            case DETAIL:
                Intent intent = new Intent(OrderFormCenterActivity.this, OrderInfoActivity.class);
                intent.putExtra(OrderInfoActivity.KEY_FOR_ORDER_ID, mAdapter.getItem(position).getOrderId());
                startActivity(intent);
                break;
            case PAY:
                Intent payIntent = new Intent(OrderFormCenterActivity.this,CommitOrderActivity.class);
                payIntent.putExtra(CommitOrderActivity.KEY_FOR_GO_IN_TYPE,CommitOrderActivity.GO_IN_TYPE_ORDER_LIST);
                payIntent.putExtra(CommitOrderActivity.KEY_FOR_ORDER_BEAN,mAdapter.getItem(position));
                startActivity(payIntent);
                break;
            case APPOINTMENT:
                // 预约
                break;
            case HUAKOU:
                confirmPay(mAdapter.getItem(position));
                break;
        }
    }

    @Inject
    ImageLoader mImageLoader;
    private int times = 1;

    CustomDialog confirmPayDialog = null;
    private void confirmPay(final OrderBean orderBean){
        confirmPayDialog = CustomDialog.create(getSupportFragmentManager())
                .setViewListener(new CustomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        TextView et = view.findViewById(R.id.count);
                        et.setText(""+times);
                        view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(times < orderBean.getGoodsList().get(0).getSurplusTimes()){
                                    times++;
                                    et.setText(""+times);
                                }
                            }
                        });
                        view.findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(times > 1){
                                    times--;
                                    et.setText(""+times);
                                }
                            }
                        });
                        GoodsOrderBean goodsOrderBean = orderBean.getGoodsList().get(0);
                        OrderFormCenterActivity.this.mImageLoader.loadImage(OrderFormCenterActivity.this,
                                ImageConfigImpl
                                        .builder()
                                        .placeholder(R.drawable.place_holder_img)
                                        .url(goodsOrderBean.getImage())
                                        .imageView(view.findViewById(R.id.image))
                                        .build());
                        ((TextView)view.findViewById(R.id.name)).setText(goodsOrderBean.getName());
                        MoneyView moneyView = view.findViewById(R.id.price);
                        moneyView.setMoneyText(ArmsUtils.formatLong(orderBean.getPayMoney()));
                        view.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                GoodsOrderBean goodsOrderBean1 = orderBean.getGoodsList().get(0);
                                mPresenter.orderHuakou(goodsOrderBean1.getReservationId(),orderBean.getOrderId(),times);
                            }
                        });
                    }
                })
                .setLayoutRes(R.layout.order_center_huakou_dialog_layout)
                .setDimAmount(0.5f)
                .isCenter(true)
                .setCancelOutside(false)
                .setWidth(556)
                .show();
    }

    public void huakouOk(boolean isOk){
        if(isOk){
            if(confirmPayDialog != null){
                confirmPayDialog.dismiss();
                confirmPayDialog = null;
                mPresenter.getOrderList(true);
            }
        }else{

        }
    }
}
