package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.ShopAppointmentInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.ShopAppointmentInfoActivity;

@ActivityScope
@Component(modules = ShopAppointmentInfoModule.class, dependencies = AppComponent.class)
public interface ShopAppointmentInfoComponent {
    void inject(ShopAppointmentInfoActivity activity);
}