package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.File;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ChangeHospitalImageContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalImageRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalImageResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitaInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


@ActivityScope
public class ChangeHospitalImagePresenter extends BasePresenter<ChangeHospitalImageContract.Model, ChangeHospitalImageContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ChangeHospitalImagePresenter(ChangeHospitalImageContract.Model model, ChangeHospitalImageContract.View rootView) {
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

    public void uploadImage(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        mModel.uploadImage("3", body)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.isSuccess()) {
                            requestOrderList(response.getResult().getUrl());
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    private void requestOrderList(String uri) {

        ChangeHospitalImageRequest request = new ChangeHospitalImageRequest();
        request.setImage(uri);
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.changeHospitalImage(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ChangeHospitalImageResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ChangeHospitalImageResponse response) {
                        if (response.isSuccess()) {
                            HospitaInfoBean hospitaInfoBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO);
                            hospitaInfoBean.setImage(uri);
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO,hospitaInfoBean);
                            mRootView.showMessage("上传成功");
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });

    }
}
