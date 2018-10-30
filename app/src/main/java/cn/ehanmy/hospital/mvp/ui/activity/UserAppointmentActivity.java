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
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.app.EventBusTags;
import cn.ehanmy.hospital.di.component.DaggerUserAppointmentComponent;
import cn.ehanmy.hospital.di.module.UserAppointmentModule;
import cn.ehanmy.hospital.mvp.contract.UserAppointmentContract;
import cn.ehanmy.hospital.mvp.presenter.UserAppointmentPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.HeightItemDecoration;
import cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.UserAppointmentAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.UserAppointmentAdapter.OnChildItemClickLinstener;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class UserAppointmentActivity extends BaseActivity<UserAppointmentPresenter> implements UserAppointmentContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, OnChildItemClickLinstener, TabLayout.OnTabSelectedListener, KAppointmentAdapter.OnChildItemClickLinstener {

    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.title_Layout)
    View title_Layout;
    @BindView(R.id.search_layout)
    View search_layout;
    @BindView(R.id.search_btn)
    View search;
    @BindView(R.id.clear_btn)
    View clear;
    @BindView(R.id.search_key)
    EditText searchKey;
    @BindView(R.id.typeTab)
    TabLayout typeLayout;
    @BindView(R.id.statusTab)
    TabLayout statusLayout;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    UserAppointmentAdapter mAdapter;
    @Inject
    KAppointmentAdapter kAdapter;
    @BindView(R.id.contentList)
    RecyclerView contentList;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_date)
    View onDateV;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems = true;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserAppointmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userAppointmentModule(new UserAppointmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_appointment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleTV.setText("预约中心");
        backV.setOnClickListener(this);
        typeLayout.addTab(typeLayout.newTab().setTag("type").setText("生美/科美"));
        typeLayout.addTab(typeLayout.newTab().setTag("type").setText("医美"));
        provideCache().put("type", 0);
        provideCache().put("status", "0");
        statusLayout.addTab(statusLayout.newTab().setTag("0").setText("新预约"));
        statusLayout.addTab(statusLayout.newTab().setTag("1").setText("已确认"));
        statusLayout.addTab(statusLayout.newTab().setTag("2").setText("已完成"));
        statusLayout.addTab(statusLayout.newTab().setTag("").setText("全部"));
        typeLayout.addOnTabSelectedListener(this);
        statusLayout.addOnTabSelectedListener(this);
        LinearLayout linearLayout = (LinearLayout) statusLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tablayout_divider_vertical));
        statusLayout.addOnTabSelectedListener(this);
        ArmsUtils.configRecyclerView(contentList, mLayoutManager);
        contentList.addItemDecoration(new HeightItemDecoration(8));
        mAdapter.setOnChildItemClickLinstener(this);
        kAdapter.setOnChildItemClickLinstener(this);
        contentList.setAdapter(kAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        initPaginate();
    }


    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.getAppointment(false);
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
        onDateV.setVisibility(hasDate ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    public void setLoadedAllItems(boolean has) {
        this.hasLoadedAllItems = has;
    }


    public Activity getActivity() {
        return this;
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
                break;
        }
    }

    @Subscriber(tag = EventBusTags.CHANGE_APPOINTMENT_TIME)
    private void updateAppointmentInfo(int index) {
        mPresenter.getAppointment(true);
    }


    @Override
    public void onRefresh() {
        mPresenter.getAppointment(true);
    }

    @Override
    public void onChildItemClick(View v, UserAppointmentAdapter.ViewName viewname, int position) {
        switch (viewname) {
            case CHANGE_APPOINTMENT:
                Intent intent2 = new Intent(UserAppointmentActivity.this, ChoiceTimeActivity.class);
                intent2.putExtra("from", "makeUserAppointment");
                intent2.putExtra("reservationId", mAdapter.getItem(position).getReservationId());
                intent2.putExtra("goodsId", mAdapter.getInfos().get(position).getGoods().getGoodsId());
                intent2.putExtra("merchId", mAdapter.getInfos().get(position).getGoods().getMerchId());
                ArmsUtils.startActivity(intent2);
                break;
            case CANCEL:
                provideCache().put("reservationId", mAdapter.getItem(position).getReservationId());
                mPresenter.cancelAppointment();
                break;
            case INFO:
                Intent intent = new Intent(ArmsUtils.getContext(), UserAppointmentInfoActivity.class);
                intent.putExtra(UserAppointmentInfoActivity.KEY_FOR_APPOINTMENT_ID, mAdapter.getItem(position).getReservationId());
                ArmsUtils.startActivity(intent);
                break;
        }
    }

    @Override
    public void onChildItemClick(View v, KAppointmentAdapter.ViewName viewname, int position) {
        switch (viewname) {
            case INFO:
                Intent intent = new Intent(ArmsUtils.getContext(), UserAppointmentInfoActivity.class);
                intent.putExtra(UserAppointmentInfoActivity.KEY_FOR_APPOINTMENT_ID, mAdapter.getItem(position).getReservationId());
                ArmsUtils.startActivity(intent);
                break;
            case OK:
                provideCache().put("reservationId", mAdapter.getItem(position).getReservationId());
                mPresenter.confirmAppointment();
                break;
            case CHANGE_APPOINTMENT:
                Intent intent2 = new Intent(UserAppointmentActivity.this, ChoiceTimeActivity.class);
                intent2.putExtra("from", "makeKAppointment");
                intent2.putExtra("reservationId", mAdapter.getItem(position).getReservationId());
                intent2.putExtra("goodsId", mAdapter.getInfos().get(position).getGoods().getGoodsId());
                intent2.putExtra("merchId", mAdapter.getInfos().get(position).getGoods().getMerchId());
                ArmsUtils.startActivity(intent2);
                break;
            case HUAKOU:
                provideCache().put("orderId", mAdapter.getItem(position).getProjectId());
                provideCache().put("reservationId", mAdapter.getItem(position).getReservationId());
                mPresenter.huakou();
                break;
            case CANCEL:
                provideCache().put("reservationId", mAdapter.getItem(position).getReservationId());
                mPresenter.cancelAppointment();
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if ("type".equals(tab.getTag())) {
            statusLayout.removeAllTabs();
            provideCache().put("type", tab.getPosition());
            provideCache().put("status", "0");
            switch (tab.getPosition()) {
                case 0:
                    contentList.setAdapter(kAdapter);
                    statusLayout.addTab(statusLayout.newTab().setTag("0").setText("新预约"));
                    statusLayout.addTab(statusLayout.newTab().setTag("1").setText("已确认"));
                    statusLayout.addTab(statusLayout.newTab().setTag("2").setText("已完成"));
                    statusLayout.addTab(statusLayout.newTab().setTag("").setText("全部"));
                    break;
                case 1:
                    contentList.setAdapter(mAdapter);
                    statusLayout.addTab(statusLayout.newTab().setTag("0").setText("未确认"));
                    statusLayout.addTab(statusLayout.newTab().setTag("2").setText("已完成"));
                    statusLayout.addTab(statusLayout.newTab().setTag("").setText("全部"));
                    break;
            }
            initPaginate();
        } else {
            provideCache().put("status", tab.getTag());
            mPresenter.getAppointment(true);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
