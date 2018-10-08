package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import cn.ehanmy.hospital.mvp.contract.MainContract;
import cn.ehanmy.hospital.mvp.model.MainModel;
import cn.ehanmy.hospital.mvp.model.entity.MainItem;
import cn.ehanmy.hospital.mvp.ui.adapter.MainAdapter;


@Module
public class MainModule {
    private MainContract.View view;

    /**
     * 构建MainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainContract.View provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainContract.Model provideMainModel(MainModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(view.getActivity(), 4);
    }

    @ActivityScope
    @Provides
    List<MainItem> provideMainItemList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    MainAdapter provideMainAdapter(List<MainItem> list) {
        return new MainAdapter(list);
    }
}