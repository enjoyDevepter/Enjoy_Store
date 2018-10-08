package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.UserAppointmentInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.UserAppointmentInfoActivity;

@ActivityScope
@Component(modules = UserAppointmentInfoModule.class, dependencies = AppComponent.class)
public interface UserAppointmentInfoComponent {
    void inject(UserAppointmentInfoActivity activity);
}