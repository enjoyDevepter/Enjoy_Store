package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMemberListRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMemberListResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberMiniInfoBean;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.MemberListContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class MemberListPresenter extends BasePresenter<MemberListContract.Model, MemberListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MemberListPresenter(MemberListContract.Model model, MemberListContract.View rootView) {
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

    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    List<MemberMiniInfoBean> orderBeanList;


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void requestOrderList(){
        requestOrderList(1,true);
    }

    public void nextPage(){
        requestOrderList(nextPageIndex,false);
    }

    private int nextPageIndex = 1;

    private void requestOrderList(int pageIndex,final boolean clear) {
        GetMemberListRequest request = new GetMemberListRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(10);
        UserBean userBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(userBean.getToken());

        mModel.getMemberList(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (clear) {
                        //                        mRootView.showLoading();//显示下拉刷新的进度条
                    }else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (clear)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetMemberListResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetMemberListResponse response) {
                        if (response.isSuccess()) {
                            if(clear){
                                orderBeanList.clear();
                            }
                            nextPageIndex = response.getNextPageIndex();
                            mRootView.setEnd(nextPageIndex == -1);
                            mRootView.showError(response.getMemberList().size() > 0);
                            orderBeanList.addAll(response.getMemberList());
                            mAdapter.notifyDataSetChanged();
                            mRootView.hideLoading();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

}
