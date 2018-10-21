package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoResponse;
import io.reactivex.Observable;
import cn.ehanmy.hospital.mvp.contract.LoginContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.request.LoginRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.LoginResponse;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {

    @Inject
    public LoginModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Observable<LoginResponse> login(LoginRequest loginRequest) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .login(loginRequest);
    }

    @Override
    public Observable<GetStoreInfoResponse> getStroeInfo(GetStoreInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getStoreInfo(request);
    }
}