package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberMiniInfoBean;
import cn.ehanmy.hospital.mvp.ui.adapter.MemberInfoListAdapter;
import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.MemberListContract;
import cn.ehanmy.hospital.mvp.model.MemberListModel;


@Module
public class MemberListModule {
    private MemberListContract.View view;

    /**
     * 构建MemberListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MemberListModule(MemberListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MemberListContract.View provideMemberListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MemberListContract.Model provideMemberListModel(MemberListModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideStoreAdapter(List<MemberMiniInfoBean> list) {
        return new MemberInfoListAdapter(list);
    }


    @ActivityScope
    @Provides
    List<MemberMiniInfoBean> provideOrderBeanList() {
        return new ArrayList<MemberMiniInfoBean>();
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(view.getActivity(), 3);
    }

}