package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerActivityInfoComponent;
import cn.ehanmy.hospital.di.module.ActivityInfoModule;
import cn.ehanmy.hospital.mvp.contract.ActivityInfoContract;
import cn.ehanmy.hospital.mvp.presenter.ActivityInfoPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.ui.adapter.ActivityInfoListAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.GridDividerItemDecoration;
import cn.ehanmy.hospital.mvp.ui.holder.ActivityInfoListHolder;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ActivityInfoActivity extends BaseActivity<ActivityInfoPresenter> implements ActivityInfoContract.View {

    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.add_activity)
    View add_activity;
    @BindView(R.id.tab)
    TabLayout tab;

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
        DaggerActivityInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .activityInfoModule(new ActivityInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"活动信息");
        add_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArmsUtils.startActivity(ActivityAddActivity.class);
            }
        });
        tab.addTab(tab.newTab().setText("待审核"));
        tab.addTab(tab.newTab().setText("已审核"));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    mPresenter.requestOrderList(ActivityInfoPresenter.SEARCY_TYPE_NO);
                }else{
                    mPresenter.requestOrderList(ActivityInfoPresenter.SEARCY_TYPE_YES);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ArmsUtils.configRecyclerView(contentList, mLayoutManager);
        contentList.addItemDecoration(new GridDividerItemDecoration(
                ArmsUtils.dip2px(ArmsUtils.getContext(),13), Color.TRANSPARENT
        ));
        if(mAdapter instanceof ActivityInfoListAdapter){
            ((ActivityInfoListAdapter) mAdapter).setOnChildItemClickLinstener(new ActivityInfoListHolder.OnChildItemClickLinstener() {
                @Override
                public void onChildItemClick(View v, ActivityInfoListHolder.ViewName viewname, int position) {
                    switch (viewname){
                        case EDIT:
                            Intent intent = new Intent(ActivityInfoActivity.this,ActivityAddActivity.class);
                            intent.putExtra(ActivityAddActivity.KEY_FOR_APPOINTENT,((ActivityInfoListAdapter) mAdapter).getItem(position));
                            ArmsUtils.startActivity(intent);
                            break;
                        case DELETE:
                            mPresenter.deleteActivityInfo(((ActivityInfoListAdapter) mAdapter).getItem(position).getActivityId());
                            break;
                    }
                }
            });
        }
        contentList.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestOrderList();
            }
        });
        initPaginate();

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

}
