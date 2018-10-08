package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.activity.ActivityInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.DeleteActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.DeleteActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoResponse;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ActivityInfoContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class ActivityInfoPresenter extends BasePresenter<ActivityInfoContract.Model, ActivityInfoContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public ActivityInfoPresenter(ActivityInfoContract.Model model, ActivityInfoContract.View rootView) {
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

    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    List<ActivityInfoBean> orderBeanList;


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void initData(){
        requestOrderList(currentType);
    }

    public void requestOrderList(String searcyType){
        requestOrderList(1,searcyType,true);
    }
    public void requestOrderList(){
        requestOrderList(1,currentType,true);
    }
    public static final String SEARCY_TYPE_NO = "0";  // 未审核
    public static final String SEARCY_TYPE_YES = "1";  // 已审核

    private String currentType = SEARCY_TYPE_NO;

    public void nextPage(){
        requestOrderList(nextPageIndex,currentType,false);
    }

    private int nextPageIndex = 1;

    private void requestOrderList(int pageIndex,String searchType,final boolean clear) {
        currentType = searchType;
        GetActivityInfoRequest request = new GetActivityInfoRequest();
        request.setPageIndex(pageIndex);
        request.setPageSize(10);
        request.setStatus(searchType);

        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());

        mModel.getActivityInfo(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetActivityInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetActivityInfoResponse response) {
                        if (response.isSuccess()) {
                            if(clear){
                                orderBeanList.clear();
                            }
                            nextPageIndex = response.getNextPageIndex();
                            mRootView.setEnd(nextPageIndex == -1);
                            mRootView.showError(response.getActivityInfoList().size() > 0);
                            orderBeanList.addAll(response.getActivityInfoList());
                            mAdapter.notifyDataSetChanged();
                            mRootView.hideLoading();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }


    public void deleteActivityInfo(String id) {
        DeleteActivityInfoRequest request = new DeleteActivityInfoRequest();
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        request.setToken(ub.getToken());
        request.setActivityId(id);

        mModel.deleteActivityInfo(request)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<DeleteActivityInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(DeleteActivityInfoResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showMessage("删除成功");
                            requestOrderList();
                        } else {
                            mRootView.showMessage(response.getRetDesc());
                        }
                    }
                });
    }
}
