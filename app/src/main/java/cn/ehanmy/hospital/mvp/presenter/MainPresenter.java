package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.contract.MainContract;
import cn.ehanmy.hospital.mvp.model.entity.MainItem;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.store.GetStoreInfoResponse;
import cn.ehanmy.hospital.mvp.ui.activity.LoginActivity;
import cn.ehanmy.hospital.mvp.ui.adapter.MainAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import cn.ehanmy.hospital.util.SPUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    MainAdapter mAdapter;
    @Inject
    List<MainItem> mainItems;
    int[] images = new int[]{
            R.mipmap.main_regiest_icon,
            R.mipmap.main_buy_icon,
            R.mipmap.order,
            R.mipmap.user_reservation,
            R.mipmap.hospital,
            R.mipmap.main_store_icon
    };

    int[] stores = new int[]{
            R.mipmap.main_store_icon,
            R.mipmap.activity,
            R.mipmap.main_regiest_icon,
            R.mipmap.setting
    };


    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        changeToMain(true);
    }

    public void changeToMain(boolean main) {
        String[] titles;
        if (main) {
            // 初始化数据
            titles = mApplication.getResources().getStringArray(R.array.main_title);
        } else {
            titles = mApplication.getResources().getStringArray(R.array.main_store_title);
        }
        mainItems.clear();
        for (int i = 0; i < titles.length; i++) {
            MainItem item = new MainItem();
            item.setName(titles[i]);
            item.setImageId(main ? images[i] : stores[i]);
            mainItems.add(item);
        }
        mAdapter.notifyDataSetChanged();
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
    public void checkUser(){
        UserBean ub = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER);
        GetStoreInfoRequest hospitalInfoRequest = new GetStoreInfoRequest();
        hospitalInfoRequest.setToken(ub.getToken());
        mModel.getStoreInfo(hospitalInfoRequest)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GetStoreInfoResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GetStoreInfoResponse s) {
                        mRootView.hideLoading();
                        if (s.isSuccess()) {
                            CacheUtil.saveConstant(CacheUtil.CACHE_KEY_STORE_INFO, s.getStore());
                            SPUtils.put(SPUtils.KEY_FOR_HOSPITAL_INFO, s.getStore() );
                        } else {
                            mRootView.showMessage("用户信息失效，请重新登录");
                            SPUtils.clear(ArmsUtils.getContext());
                            Intent intent = new Intent(ArmsUtils.getContext(),LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ArmsUtils.startActivity(intent);
                        }
                    }
                });
    }


}
