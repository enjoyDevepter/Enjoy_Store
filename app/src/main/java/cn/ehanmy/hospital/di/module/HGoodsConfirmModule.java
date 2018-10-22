package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.contract.HGoodsConfirmContract;
import cn.ehanmy.hospital.mvp.model.HGoodsConfirmModel;
import dagger.Module;
import dagger.Provides;


@Module
public class HGoodsConfirmModule {
    private HGoodsConfirmContract.View view;

    /**
     * 构建HGoodsConfirmModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HGoodsConfirmModule(HGoodsConfirmContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HGoodsConfirmContract.View provideHGoodsConfirmView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HGoodsConfirmContract.Model provideHGoodsConfirmModel(HGoodsConfirmModel model) {
        return model;
    }
}