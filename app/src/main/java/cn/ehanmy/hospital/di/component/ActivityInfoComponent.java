package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.ActivityInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.ActivityInfoActivity;

@ActivityScope
@Component(modules = ActivityInfoModule.class, dependencies = AppComponent.class)
public interface ActivityInfoComponent {
    void inject(ActivityInfoActivity activity);
}