package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ActivityInfoContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.activity.DeleteActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.DeleteActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoResponse;
import io.reactivex.Observable;


@ActivityScope
public class ActivityInfoModel extends BaseModel implements ActivityInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ActivityInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

      @Override
	    public Observable<GetActivityInfoResponse> getActivityInfo(GetActivityInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
	                .getActivityInfo(request);
    }


    @Override
    public Observable<DeleteActivityInfoResponse> deleteActivityInfo(DeleteActivityInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .deleteActivityInfo(request);
    }
}