package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.RelatedListContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetRelatedListRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetRelatedListResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.RelatedOrderBean;
import cn.ehanmy.hospital.mvp.ui.activity.RelatedListActivity;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class RelatedListPresenter extends BasePresenter<RelatedListContract.Model, RelatedListContract.View> {
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
    List<RelatedOrderBean> orderBeanList;
    private int nextPageIndex = 1;

    @Inject
    public RelatedListPresenter(RelatedListContract.Model model, RelatedListContract.View rootView) {
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

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void requestOrderList(){
        requestOrderList(1,true);
    }

    public void nextPage(){
        requestOrderList(nextPageIndex,false);
    }

    private void requestOrderList(int pageIndex,final boolean clear) {
        GetRelatedListRequest request = new GetRelatedListRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(10);
        Intent intent = mRootView.getActivity().getIntent();
        String projectId = intent.getStringExtra(RelatedListActivity.KEY_FOR_MEMBER_ID);
        request.setMemberId(projectId);

        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.getRelatedList(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (clear) {
                        //                        mRootView.showLoading();//显示下拉刷新的进度条
                    }else
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
                .subscribe(new ErrorHandleSubscriber<GetRelatedListResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetRelatedListResponse response) {
                        if (response.isSuccess()) {
                            if(clear){
                                orderBeanList.clear();
                            }
                            nextPageIndex = response.getNextPageIndex();
                            mRootView.setEnd(nextPageIndex == -1);
                            mRootView.showError(response.getOrderList().size() > 0);
                            orderBeanList.addAll(response.getOrderList());
                            mAdapter.notifyDataSetChanged();
                            mRootView.hideLoading();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }


    public void confirmShopAppointment(String orderId,String reservationId) {
        ConfirmShopAppointmentRequest request = new ConfirmShopAppointmentRequest();
        request.setOrderId(orderId);
        request.setReservationId(reservationId);
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.confirmShopAppointmentResponseObservable(request)
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
                .subscribe(new ErrorHandleSubscriber<ConfirmShopAppointmentResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ConfirmShopAppointmentResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showMessage("确认成功");
                            requestOrderList();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }
}
