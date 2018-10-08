package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.CommitOrderContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.GetPayStatusRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetPayStatusResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.GoPayRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GoPayResponse;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyRequest;
import cn.ehanmy.hospital.mvp.model.entity.placeOrder.GoodsBuyResponse;
import io.reactivex.Observable;


@ActivityScope
public class CommitOrderModel extends BaseModel implements CommitOrderContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CommitOrderModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<GoodsBuyResponse> placeGoodsOrder(GoodsBuyRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .placeGoodsOrder(request);
    }
    @Override
    public Observable<GoPayResponse> goPay(GoPayRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .goPay(request);
    }

    @Override
    public Observable<MemberInfoResponse> requestMemberinfo(MemberInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .requestMemberInfo(request);
    }
    @Override
    public Observable<GetPayStatusResponse> getPayStatus(GetPayStatusRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getPayStatus(request);
    }
}