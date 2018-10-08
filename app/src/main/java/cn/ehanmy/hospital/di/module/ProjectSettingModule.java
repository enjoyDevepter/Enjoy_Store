package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.ProjectSettingContract;
import cn.ehanmy.hospital.mvp.model.ProjectSettingModel;


@Module
public class ProjectSettingModule {
    private ProjectSettingContract.View view;

    /**
     * 构建ProjectSettingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ProjectSettingModule(ProjectSettingContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProjectSettingContract.View provideProjectSettingView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ProjectSettingContract.Model provideProjectSettingModel(ProjectSettingModel model) {
        return model;
    }
}