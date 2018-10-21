package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberMessageBean;
import cn.ehanmy.hospital.mvp.ui.adapter.MemberInfoListAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.MemberMessageListAdapter;
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

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideStoreAdapter(List<MemberMessageBean> list) {
        return new MemberMessageListAdapter(list);
    }


    @ActivityScope
    @Provides
    List<MemberMessageBean> provideOrderBeanList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }

}