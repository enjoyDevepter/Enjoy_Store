package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.HGoodsConfirmContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class HGoodsConfirmPresenter extends BasePresenter<HGoodsConfirmContract.Model, HGoodsConfirmContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HGoodsConfirmPresenter(HGoodsConfirmContract.Model model, HGoodsConfirmContract.View rootView) {
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

}
