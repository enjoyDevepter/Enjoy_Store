package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.HGoodsListContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Category;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryResponse;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Goods;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.OrderBy;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsFilterSecondAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsListAdapter;
import cn.ehanmy.hospital.util.CacheUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class HGoodsListPresenter extends BasePresenter<HGoodsListContract.Model, HGoodsListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    List<Category> categories;
    @Inject
    GoodsFilterSecondAdapter secondAdapter;
    @Inject
    GoodsListAdapter mAdapter;
    @Inject
    List<Goods> GoodsList;
    private int preEndIndex;
    private int lastPageIndex = 1;

    @Inject
    public HGoodsListPresenter(HGoodsListContract.Model model, HGoodsListContract.View rootView) {
        super(model, rootView);
    }


    private void getCategory() {
        CategoryRequest request = new CategoryRequest();
        request.setCmd(10204);
        mModel.getCategory(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<CategoryResponse>(mErrorHandler) {
                    @Override
                    public void onNext(CategoryResponse response) {
                        if (response.isSuccess()) {
                            categories.clear();
                            Category allCategory = new Category();
                            allCategory.setChoice(true);
                            allCategory.setName("全部商品");
                            ArrayList<Category> childsCategory = new ArrayList<>();
                            Category allChildCategory = new Category();
                            allChildCategory.setChoice(true);
                            allChildCategory.setName("全部");
                            childsCategory.add(allChildCategory);
                            allCategory.setGoodsCategoryList(childsCategory);
                            categories.add(allCategory);
                            categories.addAll(response.getGoodsCategoryList().get(0).getGoodsCategoryList());
                            secondAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void init() {
        getCategory();
        getGoodsList(true);
    }

    public void getGoodsList(boolean pullToRefresh) {

        GoodsPageRequest request = new GoodsPageRequest();
        request.setCmd(10101);
        Object secondCategoryId = mRootView.getCache().get("secondCategoryId");
        Object categoryId = mRootView.getCache().get("categoryId");
        if (secondCategoryId != null && categoryId != null) {
            request.setSecondCategoryId((String) secondCategoryId);
            request.setCategoryId((String) categoryId);
        }
        UserBean cacheUserBean = (UserBean) ArmsUtils.obtainAppComponentFromContext(ArmsUtils.getContext()).extras().get(IntelligentCache.KEY_KEEP + CacheUtil.CACHE_KEY_USER);
        request.setToken(cacheUserBean.getToken());

        if (!ArmsUtils.isEmpty(String.valueOf(mRootView.getCache().get("orderByField")))) {
            OrderBy orderBy = new OrderBy();
            orderBy.setField((String) mRootView.getCache().get("orderByField"));
            orderBy.setAsc((Boolean) mRootView.getCache().get("orderByAsc"));
            request.setOrderBy(orderBy);
        }

        if (pullToRefresh) lastPageIndex = 1;
        request.setPageIndex(lastPageIndex);//下拉刷新默认只请求第一页

        mModel.requestGoodsPage(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示下拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示上拉加载更多的进度条
                })
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏上拉加载更多的进度条
                })
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<GoodsPageResponse>(mErrorHandler) {
                    @Override
                    public void onNext(GoodsPageResponse response) {
                        if (response.isSuccess()) {
                            if (pullToRefresh) {
                                GoodsList.clear();
                            }
                            mRootView.showContent(response.getGoodsList().size() > 0);
                            mRootView.setLoadedAllItems(response.getNextPageIndex() == -1);
                            GoodsList.addAll(response.getGoodsList());
                            preEndIndex = GoodsList.size();//更新之前列表总长度,用于确定加载更多的起始位置
                            lastPageIndex = GoodsList.size() / 10 + 1;
                            if (pullToRefresh) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.notifyItemRangeInserted(preEndIndex, GoodsList.size());
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
