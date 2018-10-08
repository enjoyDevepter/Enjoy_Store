package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.ui.adapter.DiaryUpdateImageAdapter;
import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.ActivityAddContract;
import cn.ehanmy.hospital.mvp.model.ActivityAddModel;


@Module
public class ActivityAddModule {
    private ActivityAddContract.View view;

    /**
     * 构建ActivityAddModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ActivityAddModule(ActivityAddContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ActivityAddContract.View provideActivityAddView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ActivityAddContract.Model provideActivityAddModel(ActivityAddModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.HORIZONTAL, false);
    }

    @ActivityScope
    @Provides
    List<String> provideDiariesList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideGoodsListAdapter(List<String> images) {
        return new DiaryUpdateImageAdapter(images);
    }
}