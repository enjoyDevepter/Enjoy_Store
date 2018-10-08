package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.contract.CommitOrderContract;
import cn.ehanmy.hospital.mvp.model.CommitOrderModel;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.PayEntry;
import cn.ehanmy.hospital.mvp.ui.adapter.PayItemAdapter;
import dagger.Module;
import dagger.Provides;


@Module
public class CommitOrderModule {
    private CommitOrderContract.View view;

    /**
     * 构建CommitOrderModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommitOrderModule(CommitOrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommitOrderContract.View provideCommitOrderView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommitOrderContract.Model provideCommitOrderModel(CommitOrderModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.HORIZONTAL, false);
    }

    @ActivityScope
    @Provides
    List<PayEntry> provideStoreList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    PayItemAdapter provideStoreAdapter(List<PayEntry> list) {
        return new PayItemAdapter(list);
    }


}