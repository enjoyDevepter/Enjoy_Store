package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.OrderFormCenterContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderHuakouRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderHuakouResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.UnAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.UnAppointmentResponse;
import cn.ehanmy.hospital.mvp.ui.adapter.OrderCenterListAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class OrderFormCenterPresenter extends BasePresenter<OrderFormCenterContract.Model, OrderFormCenterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    OrderCenterListAdapter mAdapter;
    @Inject
    List<OrderBean> orderBeanList;

    private int preEndIndex;
    private int lastPageIndex = 1;

    @Inject
    public OrderFormCenterPresenter(OrderFormCenterContract.Model model, OrderFormCenterContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void initOrderList(){
        getOrderList(true);
    }

    public void getOrderList(boolean pullToRefresh) {
        OrderListRequest request = new OrderListRequest();
        request.setOrderStatus((String) mRootView.getCache().get("type"));
        String key = (String) mRootView.getCache().get("key");
        if (!TextUtils.isEmpty(key)) {
            request.setSearch(key);
        }
        UserBean cacheUserBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(cacheUserBean.getToken());
        if (pullToRefresh) lastPageIndex = 1;
        request.setPageIndex(lastPageIndex);//下拉刷新默认只请求第一页

        mModel.requestOrderListPage(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
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
                .subscribe(new ErrorHandleSubscriber<OrderListResponse>(mErrorHandler) {
                    @Override
                    public void onNext(OrderListResponse response) {
                        if (response.isSuccess()) {
                            String type = (String) mRootView.getCache().get("type");
                            if (pullToRefresh) {
                                orderBeanList.clear();
                                OrderBean orderBean = new OrderBean();
                                orderBean.setOrderStatus(type);
                                orderBeanList.add(orderBean);
                            }
                            mRootView.showError(response.getOrderList().size() > 0);
                            mRootView.setLoadedAllItems(response.getNextPageIndex() == -1);
                            List<OrderBean> orderList = response.getOrderList();

                            orderBeanList.addAll(orderList);
                            for (OrderBean ob : orderBeanList) {
                                ob.setOrderListStatus(type);
                            }
                            preEndIndex = orderBeanList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                            lastPageIndex = orderBeanList.size() / 10 + 1;
                            if (pullToRefresh) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.notifyItemRangeInserted(preEndIndex, orderBeanList.size());
                            }
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }


    public void orderHuakou(String reservationId,String orderId,int nums) {
        OrderHuakouRequest request = new OrderHuakouRequest();
        request.setNum(nums);
        request.setOrderId(orderId);
        request.setReservationId(reservationId);
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.orderHuakou(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                })
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<OrderHuakouResponse>(mErrorHandler) {
                    @Override
                    public void onNext(OrderHuakouResponse response) {
                        if (response.isSuccess()) {
                            mRootView.huakouOk(true);
                        } else {
                            mRootView.huakouOk(false);
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }


    public void unappointment(String reservationId) {
        UnAppointmentRequest request = new UnAppointmentRequest();
        request.setReservationId(reservationId);
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.unappointment(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                })
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<UnAppointmentResponse>(mErrorHandler) {
                    @Override
                    public void onNext(UnAppointmentResponse response) {
                        if (response.isSuccess()) {
                            getOrderList(true);
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
