package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerMemberListComponent;
import cn.ehanmy.hospital.di.module.MemberListModule;
import cn.ehanmy.hospital.mvp.contract.MemberListContract;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberMiniInfoBean;
import cn.ehanmy.hospital.mvp.presenter.MemberListPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.MemberInfoListAdapter;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MemberListActivity extends BaseActivity<MemberListPresenter> implements MemberListContract.View {

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @BindView(R.id.contentList)
    RecyclerView contentList;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.no_date)
    View onDateV;
    @BindView(R.id.title_Layout)
    View title;
    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean isEnd;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMemberListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .memberListModule(new MemberListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_member_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title, this, "本店会员");
        ArmsUtils.configRecyclerView(contentList, mLayoutManager);
        ((MemberInfoListAdapter) mAdapter).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemberListActivity.this, MemberInfoActivity.class);
                MemberMiniInfoBean item = ((MemberInfoListAdapter) mAdapter).getItem(position);
                intent.putExtra(MemberInfoActivity.KEY_FOR_MEMBER_ID, item.getMemberId());
                ArmsUtils.startActivity(intent);
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public Activity getActivity() {
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
}
