package cn.ehanmy.hospital.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.di.module.HGoodsConfirmModule;
import cn.ehanmy.hospital.mvp.ui.activity.HGoodsConfirmActivity;
import dagger.Component;

@ActivityScope
@Component(modules = HGoodsConfirmModule.class, dependencies = AppComponent.class)
public interface HGoodsConfirmComponent {
    void inject(HGoodsConfirmActivity activity);
}