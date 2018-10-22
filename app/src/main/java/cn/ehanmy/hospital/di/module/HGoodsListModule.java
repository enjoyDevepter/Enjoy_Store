package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.contract.HGoodsListContract;
import cn.ehanmy.hospital.mvp.model.HGoodsListModel;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Category;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Goods;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsFilterSecondAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsListAdapter;
import dagger.Module;
import dagger.Provides;


@Module
public class HGoodsListModule {
    private HGoodsListContract.View view;

    /**
     * 构建HGoodsListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HGoodsListModule(HGoodsListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HGoodsListContract.View provideHGoodsListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HGoodsListContract.Model provideHGoodsListModel(HGoodsListModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @ActivityScope
    @Provides
    List<Goods> provideStoreList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    GoodsListAdapter provideStoreAdapter(List<Goods> list) {
        return new GoodsListAdapter(list, false);
    }


    @ActivityScope
    @Provides
    List<Category> provideCategories() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    GoodsFilterSecondAdapter provideFilterSecondAdapter(List<Category> categories) {
        return new GoodsFilterSecondAdapter(categories);
    }
}