package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.HospitalInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.HospitalInfoActivity;

@ActivityScope
@Component(modules = HospitalInfoModule.class, dependencies = AppComponent.class)
public interface HospitalInfoComponent {
    void inject(HospitalInfoActivity activity);
}