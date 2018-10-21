package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ProjectSettingContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Category;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.GetCategoryGoodsListRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.GetCategoryGoodsListResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

@ActivityScope
public class ProjectSettingPresenter extends BasePresenter<ProjectSettingContract.Model, ProjectSettingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ProjectSettingPresenter(ProjectSettingContract.Model model, ProjectSettingContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void getCategory() {
        Cache<String, Object> cache = ArmsUtils.obtainAppComponentFromContext(mApplication).extras();
        CategoryRequest request = new CategoryRequest();
        mModel.getCategory(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CategoryResponse>(mErrorHandler) {
                    @Override
                    public void onNext(CategoryResponse response) {
                        if (response.isSuccess()) {
                            getProjectSetting(response.getGoodsCategoryList());
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    private void getProjectSetting(List<Category> list) {
        ProjectSettingRequest request = new ProjectSettingRequest();

        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.getProjectSetting(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<ProjectSettingResponse>(mErrorHandler) {
                    @Override
                    public void onNext(ProjectSettingResponse response) {
                        if (response.isSuccess()) {
                            mRootView.updateCategory(list, response.getMerchIdList());
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }

    public void getGoodsList(String categoryId,String secondCategoryId){
        GetCategoryGoodsListRequest request = new GetCategoryGoodsListRequest();
        request.setCategoryId(categoryId);
        request.setSecondCategoryId(secondCategoryId);


        mModel.getCategoryGoodsList(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetCategoryGoodsListResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetCategoryGoodsListResponse response) {
                        if (response.isSuccess()) {
                            mRootView.updateGoodsList(response.getMerchList());
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });

    }

    public void setProjectSetting(List<String> list) {
        SettingProjectRequest request = new SettingProjectRequest();

        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());
        request.setMerchIdList(list);

        mModel.setProjectSetting(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                })
                .subscribe(new ErrorHandleSubscriber<SettingProjectResponse>(mErrorHandler) {
                    @Override
                    public void onNext(SettingProjectResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showMessage(response.isSuccess() ? "项目设置成功!" : "项目设置失败!");
                            if(response.isSuccess()){
                                mRootView.killMyself();
                            }
                        }
                    }
                });
    }}

