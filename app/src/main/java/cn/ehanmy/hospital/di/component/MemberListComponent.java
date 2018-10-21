package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.MemberListModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.MemberListActivity;

@ActivityScope
@Component(modules = MemberListModule.class, dependencies = AppComponent.class)
public interface MemberListComponent {
    void inject(MemberListActivity activity);
}