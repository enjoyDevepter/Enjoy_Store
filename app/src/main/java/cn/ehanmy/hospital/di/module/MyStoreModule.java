package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.MainItem;
import cn.ehanmy.hospital.mvp.ui.adapter.MainAdapter;
import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.MyStoreContract;
import cn.ehanmy.hospital.mvp.model.MyStoreModel;


@Module
public class MyStoreModule {
    private MyStoreContract.View view;

    /**
     * 构建MyStoreModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MyStoreModule(MyStoreContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MyStoreContract.View provideMyStoreView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MyStoreContract.Model provideMyStoreModel(MyStoreModel model) {
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