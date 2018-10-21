package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerMemberInfoComponent;
import cn.ehanmy.hospital.di.module.MemberInfoModule;
import cn.ehanmy.hospital.mvp.contract.MemberInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.presenter.MemberInfoPresenter;

import cn.ehanmy.hospital.R;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MemberInfoActivity extends BaseActivity<MemberInfoPresenter> implements MemberInfoContract.View {

    public static final String KEY_FOR_MEMBER_ID = "key_for_member_id";
    @Inject
    ImageLoader mImageLoader;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.member_name)
    TextView member_name;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.area)
    TextView area;
    @BindView(R.id.addr)
    TextView addr;
    @BindView(R.id.message)
    EditText message;

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
        DaggerMemberInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .memberInfoModule(new MemberInfoModule(this))
                .build()
                .inject(this);
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
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_member_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ArmsUtils.configRecyclerView(contentList, mLayoutManager);
        contentList.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestOrderList();
            }
        });
        initPaginate();
        message.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_SEND:
                        String comment = message.getText().toString();
                        if (ArmsUtils.isEmpty(comment)) {
                            showMessage("请输入评论内容");
                            return true;
                        }
                        hintKeyBoard();
                        mPresenter.sendMessage(comment);
                        return true;
                }
                return false;
            }
        });
    }


    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void commentOk() {
        message.setText("");
        ArmsUtils.makeText1(this, "提交评论成功");
    }

    public void updateMemberInfo(MemberBean memberBean){
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(memberBean.getHeadImage())
                        .imageView(image)
                        .build());
        member_name.setText(memberBean.getUserName());
        name.setText(memberBean.getRealName());
        phone.setText(memberBean.getMobile());
        String sex = memberBean.getSex();
        if("0".equals(sex)){
            sex = "保密";
        }else if("1".equals(sex)){
            sex = "男";
        }else{
            sex = "女";
        }
        this.sex.setText(sex);
        area.setText(memberBean.getCity().getName());
        addr.setText(memberBean.getAddress());
        age.setText(""+memberBean.getAge());
    }

    @Override
    public void showLoading() {

    }


    public void updateRecyclerViewHeight() {
        RecyclerView.Adapter adapter = contentList.getAdapter();
        if (adapter.getItemCount() == 0) {
            contentList.setVisibility(View.GONE);
            return;
        }
        ViewGroup.LayoutParams layoutParams = swipeRefreshLayout.getLayoutParams();
        layoutParams.height = 150 * (adapter.getItemCount() < 10 ? adapter.getItemCount() : 10);
        swipeRefreshLayout.setLayoutParams(layoutParams);
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

    public Activity getActivity(){
        return this;
    }
}
