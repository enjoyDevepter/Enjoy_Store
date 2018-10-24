package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ChoiceHospitalContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.hospital.AllAddressResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalListRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalListResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.SimpleRequest;
import io.reactivex.Observable;


@ActivityScope
public class ChoiceHospitalModel extends BaseModel implements ChoiceHospitalContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ChoiceHospitalModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<AllAddressResponse> getAllAddressList(SimpleRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getAllAddressList(request);
    }

    @Override
    public Observable<HospitalListResponse> getHospitals(HospitalListRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getHospitals(request);
    }

}