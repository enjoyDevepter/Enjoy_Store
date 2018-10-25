package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.contract.UserAppointmentContract;
import cn.ehanmy.hospital.mvp.model.UserAppointmentModel;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.UserAppointmentAdapter;
import dagger.Module;
import dagger.Provides;


@Module
public class UserAppointmentModule {
    private UserAppointmentContract.View view;

    /**
     * 构建UserAppointmentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserAppointmentModule(UserAppointmentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserAppointmentContract.View provideUserAppointmentView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserAppointmentContract.Model provideUserAppointmentModel(UserAppointmentModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    UserAppointmentAdapter provideStoreAdapter(List<OrderProjectDetailBean> list) {
        return new UserAppointmentAdapter(list);
    }

    @ActivityScope
    @Provides
    KAppointmentAdapter provideKStoreAdapter(List<OrderProjectDetailBean> list) {
        return new KAppointmentAdapter(list);
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