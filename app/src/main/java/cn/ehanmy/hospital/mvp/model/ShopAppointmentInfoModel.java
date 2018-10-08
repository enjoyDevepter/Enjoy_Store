package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ShopAppointmentInfoContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ShopAppointmentInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ShopAppointmentInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentInfoResponse;
import io.reactivex.Observable;


@ActivityScope
public class ShopAppointmentInfoModel extends BaseModel implements ShopAppointmentInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ShopAppointmentInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<ShopAppointmentInfoResponse> shopAppointmentInfo(ShopAppointmentInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .shopAppointmentInfo(request);
    }
}