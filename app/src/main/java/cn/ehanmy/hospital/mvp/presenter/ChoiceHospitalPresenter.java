package cn.ehanmy.hospital.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.ChoiceHospitalContract;
import cn.ehanmy.hospital.mvp.model.entity.AreaAddress;
import cn.ehanmy.hospital.mvp.model.entity.Hospital;
import cn.ehanmy.hospital.mvp.model.entity.hospital.AllAddressResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalListRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalListResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.OrderBy;
import cn.ehanmy.hospital.mvp.model.entity.hospital.SimpleRequest;
import cn.ehanmy.hospital.mvp.ui.adapter.HospitalListAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class ChoiceHospitalPresenter extends BasePresenter<ChoiceHospitalContract.Model, ChoiceHospitalContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    List<AreaAddress> addressList;
    @Inject
    HospitalListAdapter mAdapter;
    @Inject
    List<Hospital> hospitals;

    private int preEndIndex;
    private int lastPageIndex = 1;

    @Inject
    public ChoiceHospitalPresenter(ChoiceHospitalContract.Model model, ChoiceHospitalContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        getAllAddressList();
        getHospital(true);
    }


    private void getAllAddressList() {
        SimpleRequest request = new SimpleRequest();
        request.setCmd(902);

        mModel.getAllAddressList(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<AllAddressResponse>(mErrorHandler) {
                    @Override
                    public void onNext(AllAddressResponse response) {
                        if (response.isSuccess()) {
                            addressList.clear();
                            addressList.addAll(response.getAreaList());
                        }
                    }
                });
    }


    public void getHospital(boolean pullToRefresh) {
        Cache<String, Object> cache = ArmsUtils.obtainAppComponentFromContext(mRootView.getActivity()).extras();
        HospitalListRequest request = new HospitalListRequest();
        request.setCountyId((String) mRootView.getCache().get("county"));
        request.setCityId((String) mRootView.getCache().get("city"));
        request.setProvinceId((String) mRootView.getCache().get("province"));
        request.setGoodsId((String) mRootView.getCache().get("goodsId"));
        request.setMerchId((String) mRootView.getCache().get("merchId"));
        request.setLon(String.valueOf(cache.get("lon")));
        request.setLat(String.valueOf(cache.get("lat")));
        if (pullToRefresh) lastPageIndex = 1;
        request.setPageIndex(lastPageIndex);//下拉刷新默认只请求第一页
        List<OrderBy> orderByList = new ArrayList<>();
        OrderBy orderBy = new OrderBy();
        orderBy.setField("distance");
        orderBy.setAsc(false);
        orderByList.add(orderBy);
        request.setOrderBys(orderByList);
        mModel.getHospitals(request)
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
                .subscribe(new ErrorHandleSubscriber<HospitalListResponse>(mErrorHandler) {
                    @Override
                    public void onNext(HospitalListResponse response) {
                        if (response.isSuccess()) {
                            mRootView.showConent(response.getHospitalList().size() > 0);
                            if (pullToRefresh) {
                                hospitals.clear();
                            }
                            mRootView.setLoadedAllItems(response.getNextPageIndex() == -1);
                            hospitals.addAll(response.getHospitalList());
                            preEndIndex = hospitals.size();//更新之前列表总长度,用于确定加载更多的起始位置
                            lastPageIndex = hospitals.size() / 10 + 1;
                            if (pullToRefresh) {
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mAdapter.notifyItemRangeInserted(preEndIndex, hospitals.size());
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
