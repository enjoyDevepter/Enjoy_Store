package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.RelatedOrderBean;
import cn.ehanmy.hospital.mvp.ui.adapter.RelatedListAdapter;
import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.RelatedListContract;
import cn.ehanmy.hospital.mvp.model.RelatedListModel;


@Module
public class RelatedListModule {
    private RelatedListContract.View view;

    /**
     * 构建RelatedListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RelatedListModule(RelatedListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RelatedListContract.View provideRelatedListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RelatedListContract.Model provideRelatedListModel(RelatedListModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideStoreAdapter(List<RelatedOrderBean> list) {
        return new RelatedListAdapter(list);
    }


    @ActivityScope
    @Provides
    List<RelatedOrderBean> provideOrderBeanList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }

}