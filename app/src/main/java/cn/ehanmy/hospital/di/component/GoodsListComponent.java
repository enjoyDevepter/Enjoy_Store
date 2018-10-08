package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.GoodsListModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.GoodsListActivity;

@ActivityScope
@Component(modules = GoodsListModule.class, dependencies = AppComponent.class)
public interface GoodsListComponent {
    void inject(GoodsListActivity activity);
}