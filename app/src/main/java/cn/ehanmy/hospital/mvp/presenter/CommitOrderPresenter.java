package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;
import android.widget.ImageView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.CommitOrderContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Goods;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetPayStatusRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GoPayRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GoPayResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderMemberInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderPayRequest;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyRequest;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.mvp.ui.activity.CommitOrderActivity;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class CommitOrderPresenter extends BasePresenter<CommitOrderContract.Model, CommitOrderContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CommitOrderPresenter(CommitOrderContract.Model model, CommitOrderContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init(){
        int type = mRootView.getActivity().getIntent().getIntExtra(CommitOrderActivity.KEY_FOR_GO_IN_TYPE,0);
        switch (type){
            case CommitOrderActivity.GO_IN_TYPE_CONFIRM:
                placeGoodsOrder();
                break;
            case CommitOrderActivity.GO_IN_TYPE_ORDER_LIST:
                goPay();
                break;
        }
    }

    public void goPay(){
        Intent intent = mRootView.getActivity().getIntent();
        OrderBean orderBean = (OrderBean) intent.getSerializableExtra(CommitOrderActivity.KEY_FOR_ORDER_BEAN);
        UserBean userBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        GoPayRequest request = new GoPayRequest();
        request.setOrderId(orderBean.getOrderId());
        request.setToken(userBean.getToken());

        OrderMemberInfoBean member = orderBean.getMember();
        if(member != null){
            requestHospitalInfo(member.getMobile());
        }

        mModel.goPay(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribe(new ErrorHandleSubscriber<GoPayResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GoPayResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showPaySuccess(response,orderBean);
                            mRootView.updatePayEntry(response.getPayEntryList());

                        }else{
                            mRootView.showMessage(response.getRetDesc());
                            mRootView.killMyself();
                        }
                    }
                });

    }

    public void requestHospitalInfo(String username) {
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest();
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        memberInfoRequest.setUsername(username);
        memberInfoRequest.setToken(user.getToken());
        mModel.requestMemberinfo(memberInfoRequest)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        MemberBean member = response.getMember();
                        mRootView.updateMember(member);
                    } else {

                    }
                });
    }


    public void getPayStatus(String orderId) {
        GetPayStatusRequest request = new GetPayStatusRequest();
        request.setOrderId(orderId);
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(user.getToken());
        mModel.getPayStatus(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        if("1".equals(response.getPayStatus())){
                            // 已支付
                            mRootView.payOk(response.getOrderId(),response.getOrderTime());
                        }else if("0".equals(response.getPayStatus())){
                            // 未支付
                            mRootView.showPayError(response.getRemind());
                        }
                    } else {
                        mRootView.showMessage(response.getRetDesc());
                    }
                });
    }


    public void orderPay(String payId,long money,String orderId) {
        OrderPayRequest request = new OrderPayRequest();
        request.setAmount(money);
        request.setPayId(payId);
        request.setOrderId(orderId);
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(user.getToken());
        mModel.orderPay(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        mRootView.payOk(orderId,System.currentTimeMillis());
                    } else {
                        mRootView.showMessage(response.getRetDesc());
                    }
                });
    }


    public void orderPay(String payId, long money, String orderId, ImageView imageView) {
        OrderPayRequest request = new OrderPayRequest();
        request.setAmount(money);
        request.setPayId(payId);
        request.setOrderId(orderId);
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(user.getToken());
        mModel.orderPay(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                }).retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        String url = response.getParams();
                        mImageLoader.loadImage(ArmsUtils.getContext(),
                                ImageConfigImpl
                                        .builder()
                                        .url(url)
                                        .imageView(imageView)
                                        .build());
                    } else {
                        mRootView.showMessage(response.getRetDesc());
                    }
                });
    }

    public void placeGoodsOrder() {
        Intent intent = mRootView.getActivity().getIntent();
        GoodsConfirmResponse goodsConfirmResponse = (GoodsConfirmResponse) intent.getSerializableExtra("order_info");
        String remark = intent.getStringExtra("remark");
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        UserBean userBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        GoodsBuyRequest request = new GoodsBuyRequest();
        Goods goods = goodsConfirmResponse.getGoods();
        GoodsConfirmBean goodsConfirmBean = new GoodsConfirmBean();
        goodsConfirmBean.setSalePrice(goods.getSalePrice());
        goodsConfirmBean.setNums(goods.getNums());
        goodsConfirmBean.setMerchId(goods.getMerchId());
        goodsConfirmBean.setGoodsId(goods.getGoodsId());
        request.setGoods(goodsConfirmBean);
        request.setMemberId(memberBean.getMemberId());
        request.setMoney(goodsConfirmResponse.getMoney());
        request.setPrice(goodsConfirmResponse.getPrice());
        request.setPayMoney(goodsConfirmResponse.getPayMoney());
        request.setTotalPrice(goodsConfirmResponse.getTotalPrice());
        request.setRemark(remark);
        request.setToken(userBean.getToken());

        mModel.placeGoodsOrder(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GoodsBuyResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsBuyResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showPaySuccess(response);
                            mRootView.updatePayEntry(response.getPayEntryList());
                        }else{
                            mRootView.showMessage(response.getRetDesc());
                            mRootView.killMyself();
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
