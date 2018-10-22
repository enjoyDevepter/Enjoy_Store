package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.HGoodsListContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryResponse;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageResponse;
import io.reactivex.Observable;


@ActivityScope
public class HGoodsListModel extends BaseModel implements HGoodsListContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public HGoodsListModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<GoodsPageResponse> requestGoodsPage(GoodsPageRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .requestGoodsInfo(request);
    }

    @Override
    public Observable<CategoryResponse> getCategory(CategoryRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getCategory(request);
    }
}