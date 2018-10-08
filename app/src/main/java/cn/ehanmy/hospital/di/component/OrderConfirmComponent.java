package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.OrderConfirmModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.OrderConfirmActivity;

@ActivityScope
@Component(modules = OrderConfirmModule.class, dependencies = AppComponent.class)
public interface OrderConfirmComponent {
    void inject(OrderConfirmActivity activity);
}