package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.MemberInfoContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMemberListRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMemberListResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMessageListRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMessageListResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.SendMessageRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.SendMessageResponse;
import io.reactivex.Observable;


@ActivityScope
public class MemberInfoModel extends BaseModel implements MemberInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MemberInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override
    public Observable<MemberInfoByIdResponse> requestMemberinfoById(MemberInfoByIdRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .requestMemberInfoById(request);
    }

    @Override
    public Observable<GetMessageListResponse> getMessageList(GetMessageListRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getMessageList(request);
    }

    @Override
    public Observable<SendMessageResponse> sendMessage(SendMessageRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .sendMessage(request);
    }
}