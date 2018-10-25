package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.OrderInfoContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.order.GetBuyInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetBuyInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListResponse;
import io.reactivex.Observable;


@ActivityScope
public class OrderInfoModel extends BaseModel implements OrderInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<OrderInfoResponse> requestOrderInfo(OrderInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .orderInfo(request);
    }
    @Override
    public Observable<GetBuyInfoResponse> getBuyInfo(GetBuyInfoRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getBuyInfo(request);
    }
}