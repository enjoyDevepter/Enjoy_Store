package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.OrderInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.OrderInfoActivity;

@ActivityScope
@Component(modules = OrderInfoModule.class, dependencies = AppComponent.class)
public interface OrderInfoComponent {
    void inject(OrderInfoActivity activity);
}