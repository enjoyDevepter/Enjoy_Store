package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.app.EventBusTags;
import cn.ehanmy.hospital.di.component.DaggerChoiceTimeComponent;
import cn.ehanmy.hospital.di.module.ChoiceTimeModule;
import cn.ehanmy.hospital.mvp.contract.ChoiceTimeContract;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationTimeBean;
import cn.ehanmy.hospital.mvp.presenter.ChoiceTimePresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.DateAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.TimeAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ChoiceTimeActivity extends BaseActivity<ChoiceTimePresenter> implements ChoiceTimeContract.View, View.OnClickListener, DefaultAdapter.OnRecyclerViewItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.confirm)
    TextView confrimTV;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.date)
    RecyclerView dateRV;
    @BindView(R.id.time)
    RecyclerView timeRV;
    @Inject
    DateAdapter dateAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    TimeAdapter timeAdapter;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChoiceTimeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .choiceTimeModule(new ChoiceTimeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_choice_time; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        titleTV.setText("选择时间");
        backV.setOnClickListener(this);
        confrimTV.setOnClickListener(this);
        dateAdapter.setOnItemClickListener(this);
        timeAdapter.setOnItemClickListener(this);
        ArmsUtils.configRecyclerView(dateRV, layoutManager);
        ArmsUtils.configRecyclerView(timeRV, new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dateRV.setAdapter(dateAdapter);
        timeRV.setAdapter(timeAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        initPaginate();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
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
            case R.id.confirm:
                if (ArmsUtils.isEmpty((String) provideCache().get("appointmentsTime"))) {
                    showMessage("请选择预约时间");
                    return;
                }
                String from = getIntent().getStringExtra("from");
                if ("hAppointment".equals(from) || "placeOrder".equals(from)) {
                    EventBus.getDefault().post(dateAdapter.getInfos().get((int) provideCache().get("dateIndex")), EventBusTags.CHANGE_APPOINTMENT_TIME);
                    killMyself();
                } else {
                    mPresenter.modifyAppointmentTime();
                }
                break;
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
                    mPresenter.getAppointmentTime(false);
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

            mPaginate = Paginate.with(dateRV, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }


    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        List<ReservationDateBean> appointments = dateAdapter.getInfos();
        List<ReservationTimeBean> timeList = timeAdapter.getInfos();
        switch (viewType) {
            case R.layout.goods_filter_second_item:
                for (int i = 0; i < appointments.size(); i++) {
                    appointments.get(i).setChoose(i == position ? true : false);
                }
                timeList.clear();
                timeList.addAll(appointments.get(position).getReservationTimeList());
                for (ReservationTimeBean time : timeList) {
                    time.setChoose(false);
                }
                provideCache().put("appointmentsTime", "");
                provideCache().put("appointmentsDate", appointments.get(position).getDate());
                provideCache().put("dateIndex", position);
                dateAdapter.notifyDataSetChanged();
                timeAdapter.notifyDataSetChanged();
                break;
            case R.layout.goods_filter_third_item:
                for (int i = 0; i < timeList.size(); i++) {
                    timeList.get(i).setChoose(i == position ? true : false);
                }
                timeAdapter.notifyDataSetChanged();
                provideCache().put("appointmentsTime", timeList.get(position).getTime());
                provideCache().put("timeIndex", position);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(dateRV);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        DefaultAdapter.releaseAllHolder(timeRV);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        mPresenter.getAppointmentTime(true);
    }
}
