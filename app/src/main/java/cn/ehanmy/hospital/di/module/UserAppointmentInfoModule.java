package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.UserAppointmentInfoContract;
import cn.ehanmy.hospital.mvp.model.UserAppointmentInfoModel;


@Module
public class UserAppointmentInfoModule {
    private UserAppointmentInfoContract.View view;

    /**
     * 构建UserAppointmentInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserAppointmentInfoModule(UserAppointmentInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserAppointmentInfoContract.View provideUserAppointmentInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserAppointmentInfoContract.Model provideUserAppointmentInfoModel(UserAppointmentInfoModel model) {
        return model;
    }
}