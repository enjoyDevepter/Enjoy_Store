package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ChoiceTimeContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.GetAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.GetAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ChangeUserAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ChangeUserAppointmentTimeResponse;
import io.reactivex.Observable;


@ActivityScope
public class ChoiceTimeModel extends BaseModel implements ChoiceTimeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ChoiceTimeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<GetAppointmentTimeResponse> getUserAppointmentTime(GetAppointmentTimeRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getUserAppointmentTime(request);
    }

    @Override
    public Observable<ChangeUserAppointmentTimeResponse> changeUserAppointmentTime(ChangeUserAppointmentTimeRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .changeUserAppointmentTime(request);
    }
}