package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.contract.RegisterContract;
import cn.ehanmy.hospital.mvp.model.RegisterModel;
import dagger.Module;
import dagger.Provides;


@Module
public class RegisterModule {
    private RegisterContract.View view;

    /**
     * 构建RegisterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RegisterModule(RegisterContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RegisterContract.View provideRegisterView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RegisterContract.Model provideRegisterModel(RegisterModel model) {
        return model;
    }
}