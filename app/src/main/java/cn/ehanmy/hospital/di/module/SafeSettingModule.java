package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.SafeSettingContract;
import cn.ehanmy.hospital.mvp.model.SafeSettingModel;


@Module
public class SafeSettingModule {
    private SafeSettingContract.View view;

    /**
     * 构建SafeSettingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SafeSettingModule(SafeSettingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SafeSettingContract.View provideSafeSettingView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SafeSettingContract.Model provideSafeSettingModel(SafeSettingModel model) {
        return model;
    }
}