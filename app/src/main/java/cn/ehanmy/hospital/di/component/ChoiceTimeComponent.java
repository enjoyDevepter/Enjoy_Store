package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.ChoiceTimeModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.ChoiceTimeActivity;

@ActivityScope
@Component(modules = ChoiceTimeModule.class, dependencies = AppComponent.class)
public interface ChoiceTimeComponent {
    void inject(ChoiceTimeActivity activity);
}