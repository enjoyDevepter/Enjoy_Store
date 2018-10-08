package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.ProjectSettingModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.ProjectSettingActivity;

@ActivityScope
@Component(modules = ProjectSettingModule.class, dependencies = AppComponent.class)
public interface ProjectSettingComponent {
    void inject(ProjectSettingActivity activity);
}