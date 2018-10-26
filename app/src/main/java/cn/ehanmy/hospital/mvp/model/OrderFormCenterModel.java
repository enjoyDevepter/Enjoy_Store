package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.OrderFormCenterContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderHuakouRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderHuakouResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderListResponse;
import io.reactivex.Observable;


@ActivityScope
public class OrderFormCenterModel extends BaseModel implements OrderFormCenterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderFormCenterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<OrderListResponse> requestOrderListPage(OrderListRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .requestOrderListPage(request);
    }
    @Override
    public Observable<OrderHuakouResponse> orderHuakou(OrderHuakouRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .orderHuakou(request);
    }
}