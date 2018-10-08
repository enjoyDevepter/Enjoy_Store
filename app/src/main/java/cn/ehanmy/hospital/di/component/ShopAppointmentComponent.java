package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.ShopAppointmentModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.ShopAppointmentActivity;

@ActivityScope
@Component(modules = ShopAppointmentModule.class, dependencies = AppComponent.class)
public interface ShopAppointmentComponent {
    void inject(ShopAppointmentActivity activity);
}