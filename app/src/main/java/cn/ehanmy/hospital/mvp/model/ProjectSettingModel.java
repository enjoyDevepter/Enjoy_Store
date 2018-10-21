package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ProjectSettingContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.GetCategoryGoodsListRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.GetCategoryGoodsListResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectResponse;
import io.reactivex.Observable;


@ActivityScope
public class ProjectSettingModel extends BaseModel implements ProjectSettingContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ProjectSettingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<CategoryResponse> getCategory(CategoryRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getCategory(request);
    }
    @Override
    public Observable<ProjectSettingResponse> getProjectSetting(ProjectSettingRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getProjectSetting(request);
    }

    @Override
    public Observable<SettingProjectResponse> setProjectSetting(SettingProjectRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .setProjectSetting(request);
    }
    @Override
    public Observable<GetCategoryGoodsListResponse> getCategoryGoodsList(GetCategoryGoodsListRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getCategoryGoodsList(request);
    }
}