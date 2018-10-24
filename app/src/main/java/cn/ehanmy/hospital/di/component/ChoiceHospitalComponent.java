package cn.ehanmy.hospital.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.di.module.ChoiceHospitalModule;
import cn.ehanmy.hospital.mvp.ui.activity.ChoiceHospitalActivity;
import dagger.Component;

@ActivityScope
@Component(modules = ChoiceHospitalModule.class, dependencies = AppComponent.class)
public interface ChoiceHospitalComponent {
    void inject(ChoiceHospitalActivity activity);
}