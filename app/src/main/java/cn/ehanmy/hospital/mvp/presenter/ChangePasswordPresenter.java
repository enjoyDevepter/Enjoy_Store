package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ChangePasswordContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class ChangePasswordPresenter extends BasePresenter<ChangePasswordContract.Model, ChangePasswordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ChangePasswordPresenter(ChangePasswordContract.Model model, ChangePasswordContract.View rootView) {
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

    public void changePassword(String oldPassword,String newPassword,String confirm) {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldUserPwd(oldPassword);
        request.setNewUserPwd(newPassword);
        request.setConfirmUserPwd(confirm);

        String token= ((UserBean)CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER)).getToken();
        request.setToken(token);

        mModel.changePassword(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ChangePasswordResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ChangePasswordResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showMessage("密码修改成功");
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

}
