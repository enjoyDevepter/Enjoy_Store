package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import java.util.List;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.MainItem;
import cn.ehanmy.hospital.mvp.ui.adapter.MainAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.MyStoreContract;


@ActivityScope
public class MyStorePresenter extends BasePresenter<MyStoreContract.Model, MyStoreContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyStorePresenter(MyStoreContract.Model model, MyStoreContract.View rootView) {
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
    MainAdapter mAdapter;
    @Inject
    List<MainItem> mainItems;
    int[] images = new int[]{
            R.mipmap.main_store_icon,
            R.mipmap.activity,
            R.mipmap.main_regiest_icon,
            R.mipmap.setting
    };


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        // 初始化数据
        String[] titles = mApplication.getResources().getStringArray(R.array.main_store_title);
        for (int i = 0; i < titles.length; i++) {
            MainItem item = new MainItem();
            item.setName(titles[i]);
            item.setImageId(images[i]);
            mainItems.add(item);
        }
        mAdapter.notifyDataSetChanged();
    }
}
