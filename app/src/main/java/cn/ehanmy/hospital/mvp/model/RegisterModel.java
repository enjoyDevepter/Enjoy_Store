package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.RegisterContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.reg.RegisterRequest;
import cn.ehanmy.hospital.mvp.model.entity.reg.RegisterResponse;
import cn.ehanmy.hospital.mvp.model.entity.reg.VeritfyRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import io.reactivex.Observable;


@ActivityScope
public class RegisterModel extends BaseModel implements RegisterContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public RegisterModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<RegisterResponse> register(VeritfyRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .register(request);
    }

    @Override
    public Observable<BaseResponse> register(RegisterRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .register(request);
    }

    @Override
    public Observable<BaseResponse> getVerify(VeritfyRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getVerify(request);
    }
}