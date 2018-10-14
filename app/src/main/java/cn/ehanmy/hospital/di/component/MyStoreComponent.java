package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.MyStoreModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.MyStoreActivity;

@ActivityScope
@Component(modules = MyStoreModule.class, dependencies = AppComponent.class)
public interface MyStoreComponent {
    void inject(MyStoreActivity activity);
}