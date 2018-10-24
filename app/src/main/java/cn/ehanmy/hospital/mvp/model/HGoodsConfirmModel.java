package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.HGoodsConfirmContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmWithSpecRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.HGoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import io.reactivex.Observable;


@ActivityScope
public class HGoodsConfirmModel extends BaseModel implements HGoodsConfirmContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public HGoodsConfirmModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<GoodsConfirmResponse> confirmGoods(GoodsConfirmRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .confirmGoods(request);
    }

    @Override
    public Observable<GoodsConfirmResponse> confirmGoodsWithSpec(GoodsConfirmWithSpecRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .confirmGoodsWithSpec(request);
    }

    @Override
    public Observable<BaseResponse> makeAppointment(HGoodsConfirmRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .makeAppointment(request);
    }

}