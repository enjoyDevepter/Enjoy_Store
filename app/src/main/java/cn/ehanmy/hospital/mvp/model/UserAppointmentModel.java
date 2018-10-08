package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.UserAppointmentContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.model.entity.UserAppointment;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.CancelAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.CancelAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ConfirmAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ConfirmAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.HuakouRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.HuakouResponse;
import io.reactivex.Observable;


@ActivityScope
public class UserAppointmentModel extends BaseModel implements UserAppointmentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    public static final String SEARCH_TYPE_NEW = "0";
    public static final String SEARCH_TYPE_CONFIRMED = "1";
    public static final String SEARCH_TYPE_OVER = "2";
    public static final String SEARCH_TYPE_ALL = null;

    @Inject
    public UserAppointmentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GetUserAppointmentPageResponse> getUserAppintmentPage(GetUserAppointmentPageRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getUserAppointmentPage(request);
    }
    @Override
    public Observable<ConfirmAppointmentResponse> confirmAppointment(ConfirmAppointmentRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .confirmAppointment(request);
    }
    @Override
    public Observable<CancelAppointmentResponse> cancelAppointment(CancelAppointmentRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .cancelAppointment(request);
    }
    @Override
    public Observable<HuakouResponse> huakou(HuakouRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .huakou(request);
    }
}