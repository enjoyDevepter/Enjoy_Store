package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationTimeBean;
import cn.ehanmy.hospital.mvp.ui.adapter.DateAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.TimeAdapter;
import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.ChoiceTimeContract;
import cn.ehanmy.hospital.mvp.model.ChoiceTimeModel;


@Module
public class ChoiceTimeModule {
    private ChoiceTimeContract.View view;

    /**
     * 构建ChoiceTimeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChoiceTimeModule(ChoiceTimeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChoiceTimeContract.View provideChoiceTimeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChoiceTimeContract.Model provideChoiceTimeModel(ChoiceTimeModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @ActivityScope
    @Provides
    List<ReservationDateBean> provideAppointmentList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    DateAdapter provideDateAdapter(List<ReservationDateBean> list) {
        return new DateAdapter(list);
    }

    @ActivityScope
    @Provides
    TimeAdapter provideTimeAdapter(List<ReservationTimeBean> list) {
        return new TimeAdapter(list);
    }

    @ActivityScope
    @Provides
    List<ReservationTimeBean> provideAppointmenTimetList() {
        return new ArrayList<>();
    }

}