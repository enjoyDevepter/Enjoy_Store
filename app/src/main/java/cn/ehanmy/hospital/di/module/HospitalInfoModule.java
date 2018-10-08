package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.HospitalInfoContract;
import cn.ehanmy.hospital.mvp.model.HospitalInfoModel;


@Module
public class HospitalInfoModule {
    private HospitalInfoContract.View view;

    /**
     * 构建HospitalInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HospitalInfoModule(HospitalInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HospitalInfoContract.View provideHospitalInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HospitalInfoContract.Model provideHospitalInfoModel(HospitalInfoModel model) {
        return model;
    }
}