package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.CommitOrderModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.CommitOrderActivity;

@ActivityScope
@Component(modules = CommitOrderModule.class, dependencies = AppComponent.class)
public interface CommitOrderComponent {
    void inject(CommitOrderActivity activity);
}