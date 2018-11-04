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

import cn.ehanmy.hospital.mvp.contract.UserAppointmentInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentInfoResponse;
import cn.ehanmy.hospital.mvp.ui.activity.UserAppointmentInfoActivity;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class UserAppointmentInfoPresenter extends BasePresenter<UserAppointmentInfoContract.Model, UserAppointmentInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public UserAppointmentInfoPresenter(UserAppointmentInfoContract.Model model, UserAppointmentInfoContract.View rootView) {
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
    public void init(){
        String stringExtra = mRootView.getActivity().getIntent().getStringExtra(UserAppointmentInfoActivity.KEY_FOR_APPOINTMENT_ID);

        GetUserAppointmentInfoRequest request = new GetUserAppointmentInfoRequest();
        request.setReservationId(stringExtra);

        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.getUserAppointmentInfo(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetUserAppointmentInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetUserAppointmentInfoResponse response) {
                        if (response.isSuccess()) {
                            mRootView.updateOrderInfo(response.getOrderProjectDetail());
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });

    }
}
