package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.RelatedListModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.RelatedListActivity;

@ActivityScope
@Component(modules = RelatedListModule.class, dependencies = AppComponent.class)
public interface RelatedListComponent {
    void inject(RelatedListActivity activity);
}