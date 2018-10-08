package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.BuyCenterModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.BuyCenterActivity;

@ActivityScope
@Component(modules = BuyCenterModule.class, dependencies = AppComponent.class)
public interface BuyCenterComponent {
    void inject(BuyCenterActivity activity);
}