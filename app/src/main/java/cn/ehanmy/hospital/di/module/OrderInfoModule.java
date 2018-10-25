package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.ui.adapter.OrderInfoBuyInfoAdapter;
import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.OrderInfoContract;
import cn.ehanmy.hospital.mvp.model.OrderInfoModel;


@Module
public class OrderInfoModule {
    private OrderInfoContract.View view;

    /**
     * 构建OrderInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrderInfoModule(OrderInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderInfoContract.View provideOrderInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderInfoContract.Model provideOrderInfoModel(OrderInfoModel model) {

        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideStoreAdapter(List<GoodsOrderBean> list) {
        return new OrderInfoBuyInfoAdapter(list);
    }


    @ActivityScope
    @Provides
    List<GoodsOrderBean> provideOrderBeanList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }

}