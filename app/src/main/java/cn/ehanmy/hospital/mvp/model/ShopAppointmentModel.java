package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ShopAppointmentContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.Order;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.CancelShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.CancelShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageResponse;
import io.reactivex.Observable;


@ActivityScope
public class ShopAppointmentModel extends BaseModel implements ShopAppointmentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    public static final String SEARCH_TYPE_APPOINTMENT = "0";
    public static final String SEARCH_TYPE_OVER = "2";
    public static final String SEARCH_TYPE_CANCEL = "3";
    public static final String SEARCH_TYPE_ALL = null;

    @Inject
    public ShopAppointmentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<GetShopAppointmentPageResponse> getShopAppointmentPage(GetShopAppointmentPageRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getShopAppointmentPage(request);
    }

    @Override
    public Observable<CancelShopAppointmentResponse> cancelShopAppointment(CancelShopAppointmentRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .cancelShopAppointment(request);
    }
    @Override
    public Observable<ConfirmShopAppointmentResponse> confirmShopAppointmentResponseObservable(ConfirmShopAppointmentRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .confirmShopAppointment(request);
    }
}