package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ShopAppointmentContract;
import cn.ehanmy.hospital.mvp.model.ShopAppointmentModel;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.CancelShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.CancelShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.ui.adapter.ShopAppointmentAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class ShopAppointmentPresenter extends BasePresenter<ShopAppointmentContract.Model, ShopAppointmentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    ShopAppointmentAdapter mAdapter;
    @Inject
    List<OrderProjectDetailBean> orderBeanList;
    private String currType = ShopAppointmentModel.SEARCH_TYPE_APPOINTMENT;
    private int nextPageIndex = 1;

    @Inject
    public ShopAppointmentPresenter(ShopAppointmentContract.Model model, ShopAppointmentContract.View rootView) {
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

    public void requestOrderList(String type){
        requestOrderList(1,type,true);
    }

    //    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void init(){
        requestOrderList(currType);
    }

    public void nextPage(){
        requestOrderList(nextPageIndex,currType,false);
    }

    private void requestOrderList(int pageIndex,String type,final boolean clear) {
        GetShopAppointmentPageRequest request = new GetShopAppointmentPageRequest();
        request.setPageIndex(pageIndex);
        request.setStatus(type);
        request.setPageSize(10);

        String key = mRootView.getCache().get("key") + "";
        if(!TextUtils.isEmpty(key)){
            request.setSearch(key);
        }

        UserBean cacheUserBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(cacheUserBean.getToken());

        mModel.getShopAppointmentPage(request)
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
                .subscribe(new ErrorHandleSubscriber<GetShopAppointmentPageResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetShopAppointmentPageResponse response) {
                        if (response.isSuccess()) {
                            if(clear){
                                orderBeanList.clear();
                                orderBeanList.add(new OrderProjectDetailBean());
                            }
                            currType = type;
                            nextPageIndex = response.getNextPageIndex();
                            mRootView.setEnd(nextPageIndex == -1);
                            mRootView.showError(response.getOrderProjectDetailList().size() > 0);
                            List<OrderProjectDetailBean> orderList = response.getOrderProjectDetailList();
                            orderBeanList.addAll(orderList);
                            mAdapter.notifyDataSetChanged();
                            mRootView.hideLoading();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }


    public void cancel(String reservationId) {
        CancelShopAppointmentRequest request = new CancelShopAppointmentRequest();
        request.setReservationId(reservationId);
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.cancelShopAppointment(request)
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
                .subscribe(new ErrorHandleSubscriber<CancelShopAppointmentResponse>(mErrorHandler) {
                    @Override
                    public void onNext(CancelShopAppointmentResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showMessage("取消成功");
                            init();
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
                            init();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }
}
