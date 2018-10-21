package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.RegisterContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.reg.RegisterRequest;
import cn.ehanmy.hospital.mvp.model.entity.reg.RegisterResponse;
import cn.ehanmy.hospital.mvp.model.entity.reg.VeritfyRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, RegisterContract.View rootView) {
        super(model, rootView);
    }

    public void getVerify() {
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        VeritfyRequest request = new VeritfyRequest();
        request.setToken(user.getToken());
        request.setMobile((String) mRootView.getCache().get("mobile"));
        mModel.getVerify(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if (!response.isSuccess()) {
                            mRootView.showMessage(response.getRetDesc());
                            mRootView.showVerity();
                        }
                    }
                });
    }

    public void register() {
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        VeritfyRequest request = new VeritfyRequest();
        request.setMobile((String) mRootView.getCache().get("mobile"));
        request.setVerifyCode((String) mRootView.getCache().get("verifyCode"));
        request.setToken(user.getToken());
        request.setCmd(10059);
        mModel.register(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<RegisterResponse>(mErrorHandler) {
                    @Override
                    public void onNext(RegisterResponse response) {
                        switch (response.getRetCode()) {
                            case 1012:
                                mRootView.showVerity();
                                mRootView.hasRegister(true);
                                return;
                        }
                        if (response.isSuccess()) {
                            mRootView.getCache().put("step1Token", response.getToken());
                            mRootView.inputUserInfo();
                        } else {
                            mRootView.showVerity();
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }


    public void registerLast() {
        UserBean user = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        RegisterRequest request = new RegisterRequest();
        request.setStep1Token((String) mRootView.getCache().get("step1Token"));
        request.setMobile((String) mRootView.getCache().get("mobile"));
        request.setType((String) mRootView.getCache().get("type"));
        request.setBirthday((String) mRootView.getCache().get("birthday"));
        request.setCode((String) mRootView.getCache().get("code"));
        request.setSex((String) mRootView.getCache().get("sex"));
        request.setNickName((String) mRootView.getCache().get("nickName"));
        request.setToken(user.getToken());
        mModel.register(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.isSuccess()) {
                            mRootView.registerSuccess();
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
