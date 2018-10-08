package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import org.simple.eventbus.EventBus;

import java.util.List;

import cn.ehanmy.hospital.app.EventBusTags;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ChangeUserAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ChangeUserAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationTimeBean;
import cn.ehanmy.hospital.mvp.ui.adapter.DateAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.TimeAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ChoiceTimeContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import static com.jess.arms.integration.cache.IntelligentCache.KEY_KEEP;


@ActivityScope
public class ChoiceTimePresenter extends BasePresenter<ChoiceTimeContract.Model, ChoiceTimeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    List<ReservationDateBean> appointments;
    @Inject
    DateAdapter dateAdapter;
    @Inject
    List<ReservationTimeBean> timeList;
    @Inject
    TimeAdapter timeAdapter;

    @Inject
    public ChoiceTimePresenter(ChoiceTimeContract.Model model, ChoiceTimeContract.View rootView) {
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
    void onCreate() {
        String type = mRootView.getActivity().getIntent().getStringExtra("type");
        if ("choice_time".equals(type)) {
            appointments.addAll(mRootView.getActivity().getIntent().getParcelableArrayListExtra("appointmnetInfo"));
            boolean hasChoice = false;
            for (ReservationDateBean appointment : appointments) {
                if (appointment.isChoose()) {
                    hasChoice = true;
                    timeList.addAll(appointment.getReservationTimeList());
                    break;
                }
            }
            if (!hasChoice) {
                appointments.get(0).setChoose(true);
                timeList.addAll(appointments.get(0).getReservationTimeList());
            }
            timeAdapter.notifyDataSetChanged();
            dateAdapter.notifyDataSetChanged();
        } else {
            getAppointmentTime();
        }
    }

    private void getAppointmentTime() {
        GetUserAppointmentTimeRequest request = new GetUserAppointmentTimeRequest();
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());
        request.setProjectId(mRootView.getActivity().getIntent().getStringExtra("projectId"));
        mModel.getUserAppointmentTime(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).doFinally(() -> {
            mRootView.hideLoading();//隐藏下拉刷新的进度条
        }).retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetUserAppointmentTimeResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetUserAppointmentTimeResponse response) {
                        if (response.isSuccess()) {
                            appointments.clear();
                            appointments.addAll(response.getReservationDateList());
                            if (appointments.size() > 0) {
                                appointments.get(0).setChoose(true);
                                mRootView.getCache().put("appointmentsDate", appointments.get(0).getDate());
                                timeList.addAll(appointments.get(0).getReservationTimeList());
                            }
                            timeAdapter.notifyDataSetChanged();
                            dateAdapter.notifyDataSetChanged();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    public void modifyAppointmentTime() {
        ChangeUserAppointmentTimeRequest request = new ChangeUserAppointmentTimeRequest();
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());
        request.setReservationId(mRootView.getActivity().getIntent().getStringExtra("reservationId"));
        request.setReservationDate((String) mRootView.getCache().get("appointmentsDate"));
        request.setReservationTime((String) mRootView.getCache().get("appointmentsTime"));
        mModel.changeUserAppointmentTime(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).doFinally(() -> {
            mRootView.hideLoading();//隐藏下拉刷新的进度条
        }).retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ChangeUserAppointmentTimeResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ChangeUserAppointmentTimeResponse response) {
                        if (response.isSuccess()) {
                            EventBus.getDefault().post(mRootView.getActivity().getIntent().getIntExtra("index", 0), EventBusTags.CHANGE_APPOINTMENT_TIME);
                            mRootView.killMyself();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }
}
