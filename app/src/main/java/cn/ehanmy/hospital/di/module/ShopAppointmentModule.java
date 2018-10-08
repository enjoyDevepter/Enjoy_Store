package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.ui.adapter.ShopAppointmentAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.UserAppointmentAdapter;
import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.ShopAppointmentContract;
import cn.ehanmy.hospital.mvp.model.ShopAppointmentModel;


@Module
public class ShopAppointmentModule {
    private ShopAppointmentContract.View view;

    /**
     * 构建ShopAppointmentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShopAppointmentModule(ShopAppointmentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShopAppointmentContract.View provideShopAppointmentView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ShopAppointmentContract.Model provideShopAppointmentModel(ShopAppointmentModel model) {
        return model;
    }



    @ActivityScope
    @Provides
    ShopAppointmentAdapter provideStoreAdapter(List<OrderProjectDetailBean> list) {
        return new ShopAppointmentAdapter(list);
    }


    @ActivityScope
    @Provides
    List<OrderProjectDetailBean> provideOrderBeanList() {
        ArrayList<OrderProjectDetailBean> orderBeans = new ArrayList<>();
        orderBeans.add(new OrderProjectDetailBean());
        return orderBeans;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }
}