package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.ActivityAddModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.ActivityAddActivity;

@ActivityScope
@Component(modules = ActivityAddModule.class, dependencies = AppComponent.class)
public interface ActivityAddComponent {
    void inject(ActivityAddActivity activity);
}