package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ChangeHospitalImageContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.QiniuRequest;
import cn.ehanmy.hospital.mvp.model.entity.QiniuResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalImageRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.ChangeHospitalImageResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;


@ActivityScope
public class ChangeHospitalImageModel extends BaseModel implements ChangeHospitalImageContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ChangeHospitalImageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ChangeHospitalImageResponse> changeHospitalImage(ChangeHospitalImageRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .changeHospitalImage(request);
    }

    @Override
    public Observable<QiniuResponse> getQiniuInfo(QiniuRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getQiniuInfo(request);
    }


}