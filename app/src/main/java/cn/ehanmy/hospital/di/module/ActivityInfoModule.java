package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.activity.ActivityInfoBean;
import cn.ehanmy.hospital.mvp.ui.adapter.ActivityInfoListAdapter;
import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.ActivityInfoContract;
import cn.ehanmy.hospital.mvp.model.ActivityInfoModel;


@Module
public class ActivityInfoModule {
    private ActivityInfoContract.View view;

    /**
     * 构建ActivityInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ActivityInfoModule(ActivityInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ActivityInfoContract.View provideActivityInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ActivityInfoContract.Model provideActivityInfoModel(ActivityInfoModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideStoreAdapter(List<ActivityInfoBean> list) {
        return new ActivityInfoListAdapter(list);
    }


    @ActivityScope
    @Provides
    List<ActivityInfoBean> provideOrderBeanList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(view.getActivity(), 2,LinearLayoutManager.VERTICAL, false);
    }

}