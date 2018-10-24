package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.HGoodsConfirmContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Goods;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmWithSpecBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmWithSpecRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.HGoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class HGoodsConfirmPresenter extends BasePresenter<HGoodsConfirmContract.Model, HGoodsConfirmContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HGoodsConfirmPresenter(HGoodsConfirmContract.Model model, HGoodsConfirmContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init() {
        requestConfirmInfo();
    }

    public void requestConfirmInfo() {
        Goods goods = (Goods) mRootView.getActivity().getIntent().getSerializableExtra("goods");
        GoodsConfirmRequest request = new GoodsConfirmRequest();
        request.setCmd(10102);
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        request.setMemberId(memberBean.getMemberId());
        GoodsConfirmBean goodsConfirmBean = new GoodsConfirmBean();
        goodsConfirmBean.setGoodsId(goods.getGoodsId());
        goodsConfirmBean.setMerchId(goods.getMerchId());
        goodsConfirmBean.setNums(1);
        goodsConfirmBean.setSpecValueId(goods.getSpecValueId());
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
                            mRootView.updateUI(response);
                        }
                    }
                });
    }

    public void getGoodsDetails() {
        Goods goods = (Goods) mRootView.getActivity().getIntent().getSerializableExtra("goods");
        GoodsConfirmWithSpecRequest request = new GoodsConfirmWithSpecRequest();
        request.setCmd(10104);
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
                            mRootView.updateUI(response);
                        }
                    }
                });
    }

    public void makeAppointment() {
        Goods goods = (Goods) mRootView.getActivity().getIntent().getSerializableExtra("goods");
        HGoodsConfirmRequest request = new HGoodsConfirmRequest();
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        request.setMemberId(memberBean.getMemberId());
        GoodsConfirmBean goodsConfirmBean = new GoodsConfirmBean();
        goodsConfirmBean.setGoodsId(goods.getGoodsId());
        goodsConfirmBean.setMerchId(goods.getMerchId());
        goodsConfirmBean.setNums(1);
        goodsConfirmBean.setSalePrice(goods.getSalePrice());
        request.setGoods(goodsConfirmBean);
        request.setToken(((UserBean) CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER)).getToken());
        request.setHospitalId((String) mRootView.getCache().get("hospital_id"));
        request.setReservationDate((String) mRootView.getCache().get("reservationDate"));
        request.setReservationTime((String) mRootView.getCache().get("reservationTime"));
        mModel.makeAppointment(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.isSuccess()) {
                            mRootView.payOk();
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
