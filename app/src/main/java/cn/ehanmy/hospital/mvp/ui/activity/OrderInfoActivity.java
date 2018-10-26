package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

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

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 订单详情页面
 */
public class OrderInfoActivity extends BaseActivity<OrderInfoPresenter> implements OrderInfoContract.View, TabLayout.OnTabSelectedListener {

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
    @BindView(R.id.skill_info)
    TextView skill_info;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.project_name)
    TextView project_name;
    @BindView(R.id.order_time)
    TextView order_time;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BindView(R.id.perple_info_parent)
    View perple_info_parent;

    @BindView(R.id.tab)
    TabLayout tabLayout;

    @BindView(R.id.project_info_parent)
    View project_info_parent;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @BindView(R.id.contentList)
    RecyclerView contentList;

    @BindView(R.id.times)
    TextView times;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean isEnd;
    @BindView(R.id.no_date)
    View onDateV;

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.nextPage();
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return isEnd;
                }
            };

            mPaginate = Paginate.with(contentList, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }


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
            perple_info_parent.setVisibility(VISIBLE);
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
        String specValueName = goodsOrderBean.getGoodsSpecValue().getSpecValueName();
        skill.setText(specValueName);
        skill_info.setText(specValueName);
        project_name.setText(goodsOrderBean.getName());
        if (!ArmsUtils.isEmpty(orderInfoBean.getReservationDate())) {
            order_time.setText(orderInfoBean.getReservationDate() + "  " + orderInfoBean.getReservationTime());
        }
        times.setText(""+orderInfoBean.getGoodsList().get(0).getTimes());
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
        tabLayout.addTab(tabLayout.newTab().setText("项目信息"));
        tabLayout.addTab(tabLayout.newTab().setText("消费详情"));
        tabLayout.addOnTabSelectedListener(this);
        ArmsUtils.configRecyclerView(contentList, mLayoutManager);
        contentList.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestOrderList();
            }
        });
        initPaginate();

    }



    public void updateRecyclerViewHeight() {
        RecyclerView.Adapter adapter = contentList.getAdapter();
        if (adapter.getItemCount() == 0) {
            contentList.setVisibility(View.INVISIBLE);
            return;
        }
        contentList.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = swipeRefreshLayout.getLayoutParams();
        layoutParams.height = 150 * (adapter.getItemCount() < 10 ? adapter.getItemCount() : 10);
        if(layoutParams.height < 100){
            layoutParams.height = 100;
        }
        swipeRefreshLayout.setLayoutParams(layoutParams);
    }


    @Override
    public void showLoading() {

    }
    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

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
        onDateV.setVisibility(hasDate ? INVISIBLE : VISIBLE);
        contentList.setVisibility(hasDate ? VISIBLE : INVISIBLE);
    }

    @Override
    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }
    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(contentList);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mPaginate = null;
    }
    @Override
    public Cache getCache() {
        return provideCache();
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
    public void onTabSelected(TabLayout.Tab tab) {
        if(tab.getPosition() == 0){
            // 项目信息
            swipeRefreshLayout.setVisibility(View.GONE);
            project_info_parent.setVisibility(View.VISIBLE);
        }else{
            // 消费详情
            project_info_parent.setVisibility(View.GONE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            mPresenter.requestOrderList();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
