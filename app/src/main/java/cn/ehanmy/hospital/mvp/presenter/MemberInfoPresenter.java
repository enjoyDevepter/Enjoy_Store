package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.MemberInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMessageListRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMessageListResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberMessageBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.SendMessageRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.SendMessageResponse;
import cn.ehanmy.hospital.mvp.ui.activity.MemberInfoActivity;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MemberInfoPresenter extends BasePresenter<MemberInfoContract.Model, MemberInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    List<MemberMessageBean> orderBeanList;
    private int nextPageIndex = 1;


    @Inject
    public MemberInfoPresenter(MemberInfoContract.Model model, MemberInfoContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init() {
        requestMemberInfoById();
        requestOrderList();
    }

    public void requestMemberInfoById() {
        MemberInfoByIdRequest request = new MemberInfoByIdRequest();
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setMemberId(mRootView.getActivity().getIntent().getStringExtra(MemberInfoActivity.KEY_FOR_MEMBER_ID));
        request.setToken(user.getToken());
        mModel.requestMemberinfoById(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MemberInfoByIdResponse>(mErrorHandler) {
                    @Override
                    public void onNext(MemberInfoByIdResponse s) {
                        if (s.isSuccess()) {
                            mRootView.updateMemberInfo(s.getMember());
                        }
                    }
                });
    }

    public void requestOrderList() {
        requestOrderList(1, true);
    }

    public void nextPage() {
        requestOrderList(nextPageIndex, false);
    }

    private void requestOrderList(int pageIndex, final boolean clear) {
        GetMessageListRequest request = new GetMessageListRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(10);
        request.setMemberId(mRootView.getActivity().getIntent().getStringExtra(MemberInfoActivity.KEY_FOR_MEMBER_ID));

        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.getMessageList(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (clear) {
                        mRootView.showLoading();//显示下拉刷新的进度条
                    } else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (clear)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetMessageListResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetMessageListResponse response) {
                        if (response.isSuccess()) {
                            if (clear) {
                                orderBeanList.clear();
                            }
                            nextPageIndex = response.getNextPageIndex();
                            mRootView.setEnd(nextPageIndex == -1);
                            mRootView.showError(response.getPrivateMessageList().size() > 0);
                            orderBeanList.addAll(response.getPrivateMessageList());
                            mAdapter.notifyDataSetChanged();
                            mRootView.updateRecyclerViewHeight();
                            mRootView.hideLoading();
                        }
                    }
                });
    }

    public void sendMessage(String content) {
        SendMessageRequest request = new SendMessageRequest();
        request.setMemberId(mRootView.getActivity().getIntent().getStringExtra(MemberInfoActivity.KEY_FOR_MEMBER_ID));
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());
        request.setContent(content);

        mModel.sendMessage(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<SendMessageResponse>(mErrorHandler) {
                    @Override
                    public void onNext(SendMessageResponse response) {
                        if (response.isSuccess()) {
                            mRootView.commentOk();
                            requestOrderList();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

}
