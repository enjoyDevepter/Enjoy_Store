package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.UserAppointmentContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.CancelAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.CancelAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ConfirmAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ConfirmAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.HuakouRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.HuakouResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.UserAppointmentAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class UserAppointmentPresenter extends BasePresenter<UserAppointmentContract.Model, UserAppointmentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    UserAppointmentAdapter mAdapter;
    @Inject
    KAppointmentAdapter kAdapter;
    @Inject
    List<OrderProjectDetailBean> orderBeanList;

    private int preEndIndex;
    private int lastPageIndex = 1;

    @Inject
    public UserAppointmentPresenter(UserAppointmentContract.Model model, UserAppointmentContract.View rootView) {
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
    void onCreate() {
        getAppointment(true);
    }

    public void getAppointment(boolean pullToRefresh) {
        switch (((int) mRootView.getCache().get("type"))) {
            case 0: // 生美/科美
                getAppointment(pullToRefresh, 10351);
                break;
            case 1: // 医美
                getAppointment(pullToRefresh, 10401);
                break;
        }
    }

    private void getAppointment(boolean pullToRefresh, int cmd) {
        GetUserAppointmentPageRequest request = new GetUserAppointmentPageRequest();
        request.setCmd(cmd);
        request.setSearch((String) mRootView.getCache().get("key"));
        request.setStatus((String) mRootView.getCache().get("status"));
        if (pullToRefresh) lastPageIndex = 1;
        request.setPageIndex(lastPageIndex);//下拉刷新默认只请求第一页

        UserBean cacheUserBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(cacheUserBean.getToken());

        mModel.getUserAppintmentPage(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                })
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetUserAppointmentPageResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetUserAppointmentPageResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showError(response.getOrderProjectDetailList().size() > 0);
                            if (pullToRefresh) {
                                orderBeanList.clear();
                                orderBeanList.add(new OrderProjectDetailBean());
                            }
                            mRootView.setLoadedAllItems(response.getNextPageIndex() == -1);
                            orderBeanList.addAll(response.getOrderProjectDetailList());
                            preEndIndex = orderBeanList.size() - 1;
                            lastPageIndex = (orderBeanList.size() - 1) / 10 + 1;
                            if (pullToRefresh) {
                                kAdapter.notifyDataSetChanged();
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.notifyItemRangeInserted(preEndIndex, orderBeanList.size());
                                kAdapter.notifyItemRangeInserted(preEndIndex, orderBeanList.size());
                            }
                        }
                    }
                });
    }


    public void huakou() {
        HuakouRequest request = new HuakouRequest();
        request.setOrderId((String) mRootView.getCache().get("orderId"));
        request.setReservationId((String) mRootView.getCache().get("reservationId"));
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.huakou(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<HuakouResponse>(mErrorHandler) {
                    @Override
                    public void onNext(HuakouResponse response) {
                        if (response.isSuccess()) {
                            getAppointment(true);
                        }
                    }
                });
    }


    public void confirmAppointment() {
        ConfirmAppointmentRequest request = new ConfirmAppointmentRequest();
        request.setReservationId((String) mRootView.getCache().get("reservationId"));
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.confirmAppointment(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ConfirmAppointmentResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ConfirmAppointmentResponse response) {
                        if (response.isSuccess()) {
                            getAppointment(true);
                        }
                    }
                });
    }


    public void cancelAppointment() {
        CancelAppointmentRequest request = new CancelAppointmentRequest();
        switch (((int) mRootView.getCache().get("type"))) {
            case 0: // 生美/科美
                request.setCmd(10355);
                break;
            case 1: // 医美
                request.setCmd(10404);
                break;
        }
        request.setReservationId((String) mRootView.getCache().get("reservationId"));

        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.cancelAppointment(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CancelAppointmentResponse>(mErrorHandler) {
                    @Override
                    public void onNext(CancelAppointmentResponse response) {
                        if (response.isSuccess()) {
                            getAppointment(true);
                        }
                    }
                });
    }

}
