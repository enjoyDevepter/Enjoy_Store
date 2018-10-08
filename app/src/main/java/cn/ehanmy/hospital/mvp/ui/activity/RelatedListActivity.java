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

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerRelatedListComponent;
import cn.ehanmy.hospital.di.module.RelatedListModule;
import cn.ehanmy.hospital.mvp.contract.RelatedListContract;
import cn.ehanmy.hospital.mvp.model.entity.Order;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.RelatedOrderBean;
import cn.ehanmy.hospital.mvp.presenter.RelatedListPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.ui.adapter.RelatedListAdapter;
import cn.ehanmy.hospital.mvp.ui.holder.RelatedListHolder;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RelatedListActivity extends BaseActivity<RelatedListPresenter> implements RelatedListContract.View {

    public static final String KEY_FOR_MEMBER_ID = "key_for_project_id";
    public static final String KEY_FOR_RESERVATION_ID = "key_for_reservation_id";
            // 每一个用相同的reservationID？

    @BindView(R.id.title_Layout)
    View title;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @BindView(R.id.contentList)
    RecyclerView contentList;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean isEnd;
    @BindView(R.id.no_date)
    View onDateV;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRelatedListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .relatedListModule(new RelatedListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_related_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"关联列表");
        ArmsUtils.configRecyclerView(contentList, mLayoutManager);
        String reservationId = getIntent().getStringExtra(KEY_FOR_RESERVATION_ID);
        ((RelatedListAdapter)mAdapter).setOnChildItemClickLinstener(new RelatedListHolder.OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, RelatedListHolder.ViewName viewname, int position) {
                switch (viewname){
                    case RELATED:
                        RelatedOrderBean item = ((RelatedListAdapter) mAdapter).getItem(position);
                        mPresenter.confirmShopAppointment(item.getOrderId(),reservationId);
                    break;
                }
            }
        });
        contentList.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestOrderList();
            }
        });
        initPaginate();

    }

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
    public void showLoading() {

    }
    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public Activity getActivity(){
        return this;
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
}
