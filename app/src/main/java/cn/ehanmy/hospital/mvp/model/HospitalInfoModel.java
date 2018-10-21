package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoResponse;
import io.reactivex.Observable;
import cn.ehanmy.hospital.mvp.contract.HospitalInfoContract;


@ActivityScope
public class HospitalInfoModel extends BaseModel implements HospitalInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HospitalInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ChangeHospitalInfoResponse> changeHospitalInfo(ChangeHospitalInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .changeHospitalInfo(request);
    }

    @Override
    public Observable<GetStoreInfoResponse> getStoreInfo(GetStoreInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getStoreInfo(request);
    }
}