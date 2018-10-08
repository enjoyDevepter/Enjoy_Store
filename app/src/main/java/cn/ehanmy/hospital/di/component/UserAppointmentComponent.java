package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.UserAppointmentModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.UserAppointmentActivity;

@ActivityScope
@Component(modules = UserAppointmentModule.class, dependencies = AppComponent.class)
public interface UserAppointmentComponent {
    void inject(UserAppointmentActivity activity);
}