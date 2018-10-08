package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.RelatedListContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.Order;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetRelatedListRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetRelatedListResponse;
import io.reactivex.Observable;


@ActivityScope
public class RelatedListModel extends BaseModel implements RelatedListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RelatedListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

     @Override
	    public Observable<GetRelatedListResponse> getRelatedList(GetRelatedListRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
	                .getRelatedList(request);
    }

    @Override
    public Observable<ConfirmShopAppointmentResponse> confirmShopAppointmentResponseObservable(ConfirmShopAppointmentRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .confirmShopAppointment(request);
    }

}