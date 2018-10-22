package cn.ehanmy.hospital.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.di.module.HGoodsListModule;
import cn.ehanmy.hospital.mvp.ui.activity.HGoodsListActivity;
import dagger.Component;

@ActivityScope
@Component(modules = HGoodsListModule.class, dependencies = AppComponent.class)
public interface HGoodsListComponent {
    void inject(HGoodsListActivity activity);
}