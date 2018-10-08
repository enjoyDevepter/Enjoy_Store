package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerShopAppointmentComponent;
import cn.ehanmy.hospital.di.module.ShopAppointmentModule;
import cn.ehanmy.hospital.mvp.contract.ShopAppointmentContract;
import cn.ehanmy.hospital.mvp.model.ShopAppointmentModel;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.presenter.ShopAppointmentPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.HeightItemDecoration;
import cn.ehanmy.hospital.mvp.ui.adapter.ShopAppointmentAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.UserAppointmentAdapter;
import cn.ehanmy.hospital.mvp.ui.holder.ShopAppointmentHolder;
import cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentHolder;
import cn.ehanmy.hospital.mvp.ui.widget.CustomProgressDailog;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ShopAppointmentActivity extends BaseActivity<ShopAppointmentPresenter> implements ShopAppointmentContract.View {

    @BindView(R.id.title_Layout)
    View title;

    @BindView(R.id.appointmentint)
    TextView appointment;
    @BindView(R.id.over)
    TextView over;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.search_layout)
    View search_layout;
    @BindView(R.id.code)
    View code;
    @BindView(R.id.search_btn)
    View search;
    @BindView(R.id.clear_btn)
    View clear;
    @BindView(R.id.search_key)
    EditText searchKey;
    @BindView(R.id.contentList)
    RecyclerView contentList;
    private TextView currTextView;
    private String currType;
    private int normalColor = Color.parseColor("#333333");
    private int currColor = Color.parseColor("#3DBFE8");

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    ShopAppointmentAdapter mAdapter;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Paginate mPaginate;
    private boolean isLoadingMore;

    private CustomProgressDailog progressDailog;
    private boolean isEnd;

    private View.OnClickListener onTypeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == currTextView.getId()) {
                return;
            }
            currTextView.setTextColor(normalColor);
            TextView newText = null;
            switch (v.getId()) {
                case R.id.appointmentint:
                    newText = appointment;
                    currType = ShopAppointmentModel.SEARCH_TYPE_APPOINTMENT;
                    break;
                case R.id.over:
                    currType = ShopAppointmentModel.SEARCH_TYPE_OVER;
                    newText = over;
                    break;
                case R.id.cancel:
                    newText = cancel;
                    currType = ShopAppointmentModel.SEARCH_TYPE_CANCEL;
                    break;
                case R.id.all:
                    currType = ShopAppointmentModel.SEARCH_TYPE_ALL;
                    newText = all;
                    break;
            }

            if(newText == null){
                return;
            }
            currTextView = newText;
            currTextView.setTextColor(currColor);
            mPresenter.requestOrderList(currType);
        }
    };
    private View.OnClickListener onSearchClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.search_btn:
                    doSearch();
                    break;
                case R.id.clear_btn:
                    searchKey.setText("");
                    provideCache().put("key",null);
                    mPresenter.init();
                    break;
            }
            hideImm();
        }
    };

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

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShopAppointmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopAppointmentModule(new ShopAppointmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shop_appointment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title, this, "店铺引流");
        currTextView = appointment;
        appointment.setTextColor(currColor);

        appointment.setOnClickListener(onTypeClickListener);
        over.setOnClickListener(onTypeClickListener);
        all.setOnClickListener(onTypeClickListener);
        cancel.setOnClickListener(onTypeClickListener);

        code.setVisibility(View.GONE);
        search.setOnClickListener(onSearchClickListener);
        clear.setOnClickListener(onSearchClickListener);
        ArmsUtils.configRecyclerView(contentList, mLayoutManager);

        mAdapter.setOnChildItemClickLinstener(new ShopAppointmentHolder.OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, ShopAppointmentHolder.ViewName viewname, int position) {
                switch (viewname){
                    case INFO:
                        Intent infoIntent = new Intent(ShopAppointmentActivity.this,ShopAppointmentInfoActivity.class);
                        infoIntent.putExtra(ShopAppointmentInfoActivity.KEY_FOR_APPOINTMENT_ID,mAdapter.getItem(position).getReservationId());
                        ArmsUtils.startActivity(infoIntent);
                        break;
                    case CANCEL:
                        mPresenter.cancel(mAdapter.getItem(position).getReservationId());
                        break;
                    case RELATED:
                        Intent intent = new Intent(ShopAppointmentActivity.this,RelatedListActivity.class);
                        cn.ehanmy.hospital.mvp.model.entity.shop_appointment.OrderProjectDetailBean item = mAdapter.getItem(position);
                        intent.putExtra(RelatedListActivity.KEY_FOR_MEMBER_ID, item.getMember().getMemberId());
                        intent.putExtra(RelatedListActivity.KEY_FOR_RESERVATION_ID,item.getReservationId());
                        launchActivity(intent);
                        break;
                }
            }
        });
        contentList.addItemDecoration(new HeightItemDecoration(8));
        contentList.setAdapter(mAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.init();
            }
        });

        initPaginate();
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
    public void showLoading() {

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
    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public Activity getActivity(){
        return this;
    }
    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(contentList);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mPaginate = null;
    }
    private void doSearch() {
        String s = searchKey.getText().toString();
        if (TextUtils.isEmpty(s)) {
            showMessage("请输入搜索关键字后重试");
            return;
        }

        mPresenter.init();
    }


    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }


    @BindView(R.id.no_date)
    View onDateV;

    @Override
    public void showError(boolean hasDate) {
        onDateV.setVisibility(hasDate ? INVISIBLE : VISIBLE);
        contentList.setVisibility(hasDate ? VISIBLE : INVISIBLE);
    }

    @Override
    public Cache getCache() {
        return provideCache();
    }
}
