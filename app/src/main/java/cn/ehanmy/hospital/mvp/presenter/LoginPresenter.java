package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.LoginContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.request.LoginRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.LoginResponse;
import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import cn.ehanmy.hospital.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    RxPermissions mRxPermissions;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void checkUser() {
        requestPermissions();
    }

    private void requestPermissions() {
        PermissionUtil.requestPermissionForInit(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                checkToken();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.killMyself();
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.killMyself();
            }
        }, mRxPermissions, mErrorHandler);
    }

    private void checkToken() {
        UserBean spUserbean = SPUtils.get(SPUtils.KEY_FOR_USER_INFO, new UserBean("", "", ""));
        if (!ArmsUtils.isEmpty(spUserbean.getToken())) {
            getStroeInfo();
        }
    }

    public void login(String username, String password) {
        LoginRequest request = new LoginRequest();
        request.setUsername(username);
        request.setPassword(password);
        mModel.login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<LoginResponse>(mErrorHandler) {
                    @Override
                    public void onNext(LoginResponse response) {
                        if (response.isSuccess()) {
                            UserBean value = new UserBean(username, response.getToken(), response.getSignkey());
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER, value);
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_LOGIN_NAME, username);
                            SPUtils.put(SPUtils.KEY_FOR_USER_INFO, value);
                            SPUtils.put(SPUtils.KEY_FOR_USER_NAME, username);
                            getStroeInfo();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });

    }

    private void getStroeInfo() {
        UserBean spUserbean = SPUtils.get(SPUtils.KEY_FOR_USER_INFO, new UserBean("", "", ""));
        GetStoreInfoRequest getStoreInfoRequest = new GetStoreInfoRequest();
        getStoreInfoRequest.setToken(spUserbean.getToken());
        mModel.getStroeInfo(getStoreInfoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetStoreInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetStoreInfoResponse response) {
                        if (response.isSuccess()) {
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER, spUserbean);
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_LOGIN_NAME, spUserbean.getUserName());
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_STORE_INFO, response.getStore());
                            SPUtils.put(SPUtils.KEY_FOR_STORE_INFO, response.getStore());
                            mRootView.goMainPage();
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
