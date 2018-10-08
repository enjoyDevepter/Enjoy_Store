package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.activity.ActivityInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.ChangeActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.ChangeActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ActivityAddContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static cn.ehanmy.hospital.mvp.ui.activity.ActivityAddActivity.KEY_FOR_APPOINTENT;


@ActivityScope
public class ActivityAddPresenter extends BasePresenter<ActivityAddContract.Model, ActivityAddContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    List<String> images = new ArrayList<>();

    @Inject
    public ActivityAddPresenter(ActivityAddContract.Model model, ActivityAddContract.View rootView) {
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

//        File file = new File((String) mRootView.getCache().get("imagePath"));

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/otcet-stream"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

        mModel.uploadImage("2", body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if (response.isSuccess()) {
                            images.clear();  // 只保留一张图片
                            images.add(response.getResult().getUrl());
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init(){
        ActivityInfoBean activityInfoBean = (ActivityInfoBean) mRootView.getActivity().getIntent().getSerializableExtra(KEY_FOR_APPOINTENT);

        if(activityInfoBean != null){
            images.add(activityInfoBean.getImage());
        }
    }

    public void addActivity(String title,String content) {
        AddActivityRequest request = new AddActivityRequest();
        request.setTitle(title);
        request.setContent(content);
        request.setImageList(new ArrayList<>(images));
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.addActivity(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<AddActivityResponse>(mErrorHandler) {
                    @Override
                    public void onNext(AddActivityResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showMessage("添加活动成功");
                            mRootView.killMyself();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    public void changeActivityInfo(String id,String title,String content) {
        ChangeActivityInfoRequest request = new ChangeActivityInfoRequest();
        request.setTitle(title);
        request.setContent(content);
        request.setImageList(new ArrayList<>(images));
        request.setActivityId(id);
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.changeActivityInfo(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ChangeActivityInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ChangeActivityInfoResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showMessage("编辑活动成功");
                            mRootView.killMyself();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

}
