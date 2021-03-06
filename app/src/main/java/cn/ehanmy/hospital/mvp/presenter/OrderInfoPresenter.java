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

import cn.ehanmy.hospital.mvp.contract.OrderInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.order.GetBuyInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetBuyInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoResponse;
import cn.ehanmy.hospital.mvp.ui.activity.OrderInfoActivity;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class OrderInfoPresenter extends BasePresenter<OrderInfoContract.Model, OrderInfoContract.View> {
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
    List<GoodsOrderBean> orderBeanList;
    private int nextPageIndex = 1;

    @Inject
    public OrderInfoPresenter(OrderInfoContract.Model model, OrderInfoContract.View rootView) {
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
    public void init() {
        String orderId = mRootView.getActivity().getIntent().getStringExtra(OrderInfoActivity.KEY_FOR_ORDER_ID);
        requestOrderInfo(orderId);
        requestOrderList();
    }

    public void requestOrderInfo(String orderId) {
        OrderInfoRequest orderInfoRequest = new OrderInfoRequest();
        orderInfoRequest.setOrderId(orderId);
        UserBean userBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        orderInfoRequest.setToken(userBean.getToken());


        mModel.requestOrderInfo(orderInfoRequest)
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
                .subscribe(new ErrorHandleSubscriber<OrderInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(OrderInfoResponse response) {
                        if (response.isSuccess()) {
                            mRootView.updateOrderInfo(response.getOrder());
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    public void requestOrderList(){
        requestOrderList(1,true);
    }

    public void nextPage(){
        requestOrderList(nextPageIndex,false);
    }

    private void requestOrderList(int pageIndex,final boolean clear) {
        GetBuyInfoRequest request = new GetBuyInfoRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(10);
        String stringExtra = mRootView.getActivity().getIntent().getStringExtra(OrderInfoActivity.KEY_FOR_ORDER_ID);
        request.setOrderId(stringExtra);

        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.getBuyInfo(request)
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
                .subscribe(new ErrorHandleSubscriber<GetBuyInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetBuyInfoResponse response) {
                        if (response.isSuccess()) {
                            if(clear){
                                orderBeanList.clear();
                            }
                            nextPageIndex = response.getNextPageIndex();
                            mRootView.setEnd(nextPageIndex == -1);
                            mRootView.showError(response.getGoodsList().size() > 0);
                            orderBeanList.addAll(response.getGoodsList());
                            mAdapter.notifyDataSetChanged();
                            mRootView.updateRecyclerViewHeight();
                            mRootView.hideLoading();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

}
