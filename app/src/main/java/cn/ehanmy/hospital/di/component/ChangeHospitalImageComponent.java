package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.ChangeHospitalImageModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.ChangeHospitalImageActivity;

@ActivityScope
@Component(modules = ChangeHospitalImageModule.class, dependencies = AppComponent.class)
public interface ChangeHospitalImageComponent {
    void inject(ChangeHospitalImageActivity activity);
}