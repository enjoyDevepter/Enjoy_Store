package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdResponse;
import io.reactivex.Observable;
import cn.ehanmy.hospital.mvp.contract.BuyCenterContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoResponse;


@ActivityScope
public class BuyCenterModel extends BaseModel implements BuyCenterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public BuyCenterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<MemberInfoResponse> requestMemberinfo(MemberInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .requestMemberInfo(request);
    }

    @Override
    public Observable<MemberInfoByIdResponse> requestMemberinfoById(MemberInfoByIdRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .requestMemberInfoById(request);
    }
}