package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.contract.MainContract;
import cn.ehanmy.hospital.mvp.model.entity.MainItem;
import cn.ehanmy.hospital.mvp.ui.adapter.MainAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


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
}
