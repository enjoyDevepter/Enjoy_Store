package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ChangePasswordContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ChangePasswordResponse;
import io.reactivex.Observable;


@ActivityScope
public class ChangePasswordModel extends BaseModel implements ChangePasswordContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ChangePasswordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
	public Observable<ChangePasswordResponse> changePassword(ChangePasswordRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
	                .changePassword(request);
    }
}