package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.MemberInfoContract;
import cn.ehanmy.hospital.mvp.model.MemberInfoModel;


@Module
public class MemberInfoModule {
    private MemberInfoContract.View view;

    /**
     * 构建MemberInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MemberInfoModule(MemberInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MemberInfoContract.View provideMemberInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MemberInfoContract.Model provideMemberInfoModel(MemberInfoModel model) {
        return model;
    }
}