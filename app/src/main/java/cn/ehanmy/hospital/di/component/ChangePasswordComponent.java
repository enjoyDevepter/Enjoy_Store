package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.ChangePasswordModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.ChangePasswordActivity;

@ActivityScope
@Component(modules = ChangePasswordModule.class, dependencies = AppComponent.class)
public interface ChangePasswordComponent {
    void inject(ChangePasswordActivity activity);
}