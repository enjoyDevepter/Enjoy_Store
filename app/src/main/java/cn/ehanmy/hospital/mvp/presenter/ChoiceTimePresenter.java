package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import org.simple.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.app.EventBusTags;
import cn.ehanmy.hospital.mvp.contract.ChoiceTimeContract;
import cn.ehanmy.hospital.mvp.model.entity.GetAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.GetAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ChangeUserAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ChangeUserAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationTimeBean;
import cn.ehanmy.hospital.mvp.ui.adapter.DateAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.TimeAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


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

    private int preEndIndex;
    private int lastPageIndex = 1;

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
        getAppointmentTime(true);
    }

    public void getAppointmentTime(boolean pullToRefresh) {
        GetAppointmentTimeRequest request = new GetAppointmentTimeRequest();
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        String from = mRootView.getActivity().getIntent().getStringExtra("from");

        if ("hAppointment".equals(from)) { // 医美预约
            request.setCmd(10106);
        } else if ("placeOrder".equals(from)) { // 下单中心
            request.setCmd(10160);
        } else if ("makeUserAppointment".equals(from)) {
            request.setCmd(10106);
        } else if ("makeKAppointment".equals(from)) {
            request.setCmd(10356);
        } else if ("orderCenter".equals(from) || "orderCenterDetail".equals(from)) {
            request.setCmd(10507);
            request.setProjectId(mRootView.getActivity().getIntent().getStringExtra("projectId"));
        }
        request.setGoodsId(mRootView.getActivity().getIntent().getStringExtra("goodsId"));
        request.setMerchId(mRootView.getActivity().getIntent().getStringExtra("merchId"));


        if (pullToRefresh) lastPageIndex = 1;
        request.setPageIndex(lastPageIndex);//下拉刷新默认只请求第一页

        mModel.getUserAppointmentTime(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                })
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetAppointmentTimeResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetAppointmentTimeResponse response) {
                        if (response.isSuccess()) {
                            if (pullToRefresh) {
                                timeList.clear();
                                appointments.clear();
                            }
                            appointments.addAll(response.getReservationDateList());
                            mRootView.setLoadedAllItems(response.getNextPageIndex() == -1);
                            preEndIndex = appointments.size();//更新之前列表总长度,用于确定加载更多的起始位置
                            lastPageIndex = appointments.size() / 10 + 1;

                            if (appointments.size() > 0) {
                                appointments.get(0).setChoose(true);
                                timeList.addAll(appointments.get(0).getReservationTimeList());
                            }

                            if (pullToRefresh) {
                                dateAdapter.notifyDataSetChanged();
                            } else {
                                dateAdapter.notifyItemRangeInserted(preEndIndex, appointments.size());
                            }
                            timeAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void modifyAppointmentTime() {
        ChangeUserAppointmentTimeRequest request = new ChangeUserAppointmentTimeRequest();

        String from = mRootView.getActivity().getIntent().getStringExtra("from");

        if ("makeUserAppointment".equals(from)) {
            request.setCmd(10403);
        } else if ("makeKAppointment".equals(from)) {
            request.setCmd(10354);
        } else if ("orderCenter".equals(from)) {
            request.setCmd(10505);
            request.setProjectId(mRootView.getActivity().getIntent().getStringExtra("projectId"));
        } else if ("orderCenterDetail".equals(from)) {
            request.setCmd(10506);
        }
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());
        request.setReservationId(mRootView.getActivity().getIntent().getStringExtra("reservationId"));
        request.setReservationDate((String) mRootView.getCache().get("appointmentsDate"));
        request.setReservationTime((String) mRootView.getCache().get("appointmentsTime"));
        mModel.changeUserAppointmentTime(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                }).retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ChangeUserAppointmentTimeResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ChangeUserAppointmentTimeResponse response) {
                        if (response.isSuccess()) {
                            EventBus.getDefault().post(mRootView.getCache().get("dateIndex"), EventBusTags.CHANGE_APPOINTMENT_TIME);
                            mRootView.killMyself();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }
}
