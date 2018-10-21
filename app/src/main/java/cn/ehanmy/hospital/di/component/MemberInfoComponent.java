package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.MemberInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.MemberInfoActivity;

@ActivityScope
@Component(modules = MemberInfoModule.class, dependencies = AppComponent.class)
public interface MemberInfoComponent {
    void inject(MemberInfoActivity activity);
}