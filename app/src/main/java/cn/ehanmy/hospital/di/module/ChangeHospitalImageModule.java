package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.ChangeHospitalImageContract;
import cn.ehanmy.hospital.mvp.model.ChangeHospitalImageModel;


@Module
public class ChangeHospitalImageModule {
    private ChangeHospitalImageContract.View view;

    /**
     * 构建ChangeHospitalImageModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChangeHospitalImageModule(ChangeHospitalImageContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChangeHospitalImageContract.View provideChangeHospitalImageView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChangeHospitalImageContract.Model provideChangeHospitalImageModel(ChangeHospitalImageModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }
}