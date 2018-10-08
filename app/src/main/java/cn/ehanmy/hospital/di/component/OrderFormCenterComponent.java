package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.OrderFormCenterModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.OrderFormCenterActivity;

@ActivityScope
@Component(modules = OrderFormCenterModule.class, dependencies = AppComponent.class)
public interface OrderFormCenterComponent {
    void inject(OrderFormCenterActivity activity);
}