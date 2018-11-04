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

import cn.ehanmy.hospital.mvp.contract.HospitalInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class HospitalInfoPresenter extends BasePresenter<HospitalInfoContract.Model, HospitalInfoContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HospitalInfoPresenter(HospitalInfoContract.Model model, HospitalInfoContract.View rootView) {
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
    private void initHospitalInfo() {
        GetStoreInfoRequest request = new GetStoreInfoRequest();
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());
        mModel.getStoreInfo(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetStoreInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetStoreInfoResponse response) {
                        if (response.isSuccess()) {
                            mRootView.updateUI(response.getStore());
//                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO, response.getHospital());
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    public void changeHospitalInfo(String phone,String startTime,String endTime) {
        ChangeHospitalInfoRequest request = new ChangeHospitalInfoRequest();
        request.setEndTime(endTime);
        request.setStartTime(startTime);
        request.setTellphone(phone);
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.changeHospitalInfo(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ChangeHospitalInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ChangeHospitalInfoResponse response) {
                        if (response.isSuccess()) {
                            initHospitalInfo();
                            mRootView.changeOk();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }
}
