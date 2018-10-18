package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.OrderConfirmContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Goods;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmWithSpecBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmWithSpecRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.mvp.ui.activity.CommitOrderActivity;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class OrderConfirmPresenter extends BasePresenter<OrderConfirmContract.Model, OrderConfirmContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    GoodsConfirmResponse goodsConfirmResponse;

    @Inject
    public OrderConfirmPresenter(OrderConfirmContract.Model model, OrderConfirmContract.View rootView) {
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
        requestConfirmInfo();
    }

    public void requestConfirmInfo() {
        Goods goods = (Goods) mRootView.getActivity().getIntent().getSerializableExtra("goods");
        GoodsConfirmRequest request = new GoodsConfirmRequest();
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        request.setMemberId(memberBean.getMemberId());
        GoodsConfirmBean goodsConfirmBean = new GoodsConfirmBean();
        goodsConfirmBean.setGoodsId(goods.getGoodsId());
        goodsConfirmBean.setMerchId(goods.getMerchId());
        goodsConfirmBean.setNums(1);
        goodsConfirmBean.setSalePrice(goods.getSalePrice());
        request.setGoods(goodsConfirmBean);
        request.setToken(((UserBean) CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER)).getToken());
        if (mRootView.getCache().get("money") != null) {
            request.setMoney((Long) mRootView.getCache().get("money"));
        }
        mModel.confirmGoods(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GoodsConfirmResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsConfirmResponse response) {
                        if (response.isSuccess()) {
                            goodsConfirmResponse = response;
                            mRootView.updateUI(response);
                        }
                    }
                });
    }

    public void getGoodsDetails() {
        Goods goods = (Goods) mRootView.getActivity().getIntent().getSerializableExtra("goods");
        GoodsConfirmWithSpecRequest request = new GoodsConfirmWithSpecRequest();
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        request.setMemberId(memberBean.getMemberId());
        GoodsConfirmWithSpecBean goodsConfirmBean = new GoodsConfirmWithSpecBean();
        goodsConfirmBean.setSpecValueId((String) mRootView.getCache().get("specValueId"));
        goodsConfirmBean.setGoodsId(goods.getGoodsId());
        goodsConfirmBean.setMerchId((String) mRootView.getCache().get("merchId"));
        goodsConfirmBean.setNums(1);
        goodsConfirmBean.setSalePrice(goods.getSalePrice());
        request.setGoods(goodsConfirmBean);
        if (mRootView.getCache().get("money") != null) {
            request.setMoney((Long) mRootView.getCache().get("money"));
        }
        request.setToken(((UserBean) CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER)).getToken());

        mModel.confirmGoodsWithSpec(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GoodsConfirmResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsConfirmResponse response) {
                        if (response.isSuccess()) {
                            goodsConfirmResponse = response;
                            mRootView.updateUI(response);
                        }
                    }
                });
    }

    public void placeGoodsOrder() {
        // 跳转到支付页面
        Intent payIntent = new Intent(mRootView.getActivity(), CommitOrderActivity.class);
        payIntent.putExtra(CommitOrderActivity.KEY_FOR_GO_IN_TYPE, CommitOrderActivity.GO_IN_TYPE_CONFIRM);
        payIntent.putExtra("order_info", goodsConfirmResponse);
        payIntent.putExtra("remark", (String) mRootView.getCache().get("remark"));
        ArmsUtils.startActivity(payIntent);
    }
}
