package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.BuyCenterContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class BuyCenterPresenter extends BasePresenter<BuyCenterContract.Model, BuyCenterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public BuyCenterPresenter(BuyCenterContract.Model model, BuyCenterContract.View rootView) {
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

    public void requestHospitalInfo(String username) {
        MemberInfoRequest memberInfoRequest = new MemberInfoRequest();
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        memberInfoRequest.setUsername(username);
        memberInfoRequest.setToken(user.getToken());
        mModel.requestMemberinfo(memberInfoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<MemberInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(MemberInfoResponse response) {
                        if (response.isSuccess()) {
                            MemberBean member = response.getMember();
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_MEMBER, member);
                            mRootView.updateCodeisRight(true);
                        } else {
                            mRootView.updateCodeisRight(false);
                        }
                    }
                });
    }


    public void requestMemberInfoById(String memberId) {
        MemberInfoByIdRequest request = new MemberInfoByIdRequest();
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setMemberId(memberId);
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
                            MemberBean member = s.getMember();
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_MEMBER, member);
                            mRootView.updateCodeisRight(true);
                        } else {
                            mRootView.updateCodeisRight(false);
                        }
                    }
                });
    }
}
