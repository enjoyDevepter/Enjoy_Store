package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.contract.ChoiceHospitalContract;
import cn.ehanmy.hospital.mvp.model.ChoiceHospitalModel;
import cn.ehanmy.hospital.mvp.model.entity.AreaAddress;
import cn.ehanmy.hospital.mvp.model.entity.Hospital;
import cn.ehanmy.hospital.mvp.ui.adapter.HospitalListAdapter;
import dagger.Module;
import dagger.Provides;


@Module
public class ChoiceHospitalModule {
    private ChoiceHospitalContract.View view;

    /**
     * 构建ChoiceHospitalModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChoiceHospitalModule(ChoiceHospitalContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChoiceHospitalContract.View provideChoiceHospitalView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChoiceHospitalContract.Model provideChoiceHospitalModel(ChoiceHospitalModel model) {
        return model;
    }


    @ActivityScope
    @Provides
    List<AreaAddress> provideAddressList() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @ActivityScope
    @Provides
    List<Hospital> provideStoreList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    HospitalListAdapter provideStoreAdapter(List<Hospital> list) {
        return new HospitalListAdapter(list);
    }
}