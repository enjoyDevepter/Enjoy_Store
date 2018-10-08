package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ActivityAddContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.ChangeActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.ChangeActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;


@ActivityScope
public class ActivityAddModel extends BaseModel implements ActivityAddContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ActivityAddModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> uploadImage(String type, MultipartBody.Part imgs) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .uploadImage(type, imgs);
    }


    @Override
    public Observable<AddActivityResponse> addActivity(AddActivityRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .addActivity(request);
    }
    @Override
    public Observable<ChangeActivityInfoResponse> changeActivityInfo(ChangeActivityInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .changeActivityInfo(request);
    }
}