package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.app.EventBusTags;
import cn.ehanmy.hospital.di.component.DaggerChoiceHospitalComponent;
import cn.ehanmy.hospital.di.module.ChoiceHospitalModule;
import cn.ehanmy.hospital.mvp.contract.ChoiceHospitalContract;
import cn.ehanmy.hospital.mvp.model.entity.AreaAddress;
import cn.ehanmy.hospital.mvp.presenter.ChoiceHospitalPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.HospitalListAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.SpacesItemDecoration;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ChoiceHospitalActivity extends BaseActivity<ChoiceHospitalPresenter> implements ChoiceHospitalContract.View, View.OnClickListener, DefaultAdapter.OnRecyclerViewItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.district)
    TextView districtV;
    @BindView(R.id.district_layout)
    View districtLayoutV;
    @BindView(R.id.confirm)
    View confirmV;
    @BindView(R.id.stores)
    RecyclerView storesRV;
    @BindView(R.id.no_date)
    View noDataV;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    HospitalListAdapter mAdapter;
    @Inject
    List<AreaAddress> addressList;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;

    private List<AreaAddress> options1Items = new ArrayList<>();
    private List<List<AreaAddress>> options2Items = new ArrayList<>();
    private List<List<List<AreaAddress>>> options3Items = new ArrayList<>();

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerChoiceHospitalComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .choiceHospitalModule(new ChoiceHospitalModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_choice_hospital; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        titleTV.setText("选择医院");
        backV.setOnClickListener(this);
        confirmV.setOnClickListener(this);
        districtLayoutV.setOnClickListener(this);
        districtV.setText("所在区域");

        provideCache().put("province", getIntent().getStringExtra("province"));
        provideCache().put("city", getIntent().getStringExtra("city"));
        provideCache().put("county", getIntent().getStringExtra("county"));
        provideCache().put("goodsId", getIntent().getStringExtra("goodsId"));
        provideCache().put("merchId", getIntent().getStringExtra("merchId"));

        ArmsUtils.configRecyclerView(storesRV, mLayoutManager);
        storesRV.setAdapter(mAdapter);
        storesRV.addItemDecoration(new SpacesItemDecoration(0, ArmsUtils.getDimens(ArmsUtils.getContext(), R.dimen.address_list_item_space)));
        mAdapter.setOnItemClickListener(this);
        initPaginate();
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
                break;
            case R.id.district_layout:
                showPickerView();
                break;
            case R.id.confirm:
                if (null != provideCache().get("choiceItem")) {
                    int index = (int) provideCache().get("choiceItem");
                    EventBus.getDefault().post(mAdapter.getInfos().get(index), EventBusTags.HOSPITAL_CHANGE_EVENT);
                    killMyself();
                } else {
                    showMessage("请选择医院！");
                    return;
                }
        }
    }

    private void showPickerView() {// 弹出选择器

        if (addressList.size() <= 0) {
            return;
        }

        options1Items.clear();
        options2Items.clear();
        options3Items.clear();

        options1Items.addAll(addressList);

        for (AreaAddress areaAddress : addressList) { // 遍历省份
            List<AreaAddress> cities = new ArrayList<>();
            List<List<AreaAddress>> countyList = new ArrayList<>();
            for (AreaAddress city : areaAddress.getAreaList()) {
                cities.add(city);
                List<AreaAddress> counties = new ArrayList<>();//该城市的所有地区列表
                AreaAddress countyCity = new AreaAddress();
                countyCity.setName("全部");
                counties.add(countyCity);
                for (AreaAddress county : city.getAreaList()) {
                    counties.add(county);
                }
                countyList.add(counties);
            }
            options2Items.add(cities);

            options3Items.add(countyList);
        }


        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() + " " +
                        options2Items.get(options1).get(options2).getPickerViewText() + " " +
                        options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                districtV.setText(tx);
                provideCache().put("province", options1Items.get(options1).getAreaId());
                provideCache().put("city", options2Items.get(options1).get(options2).getAreaId());
                provideCache().put("county", options3Items.get(options1).get(options2).get(options3).getAreaId());
                mPresenter.getHospital(true);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setCancelColor(Color.parseColor("#FF5fbfe3"))
                .setSubmitColor(Color.parseColor("#FF5fbfe3"))
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public void setLoadedAllItems(boolean has) {
        this.hasLoadedAllItems = has;
    }

    @Override
    public void showConent(boolean hasData) {
        storesRV.setVisibility(hasData ? View.VISIBLE : View.GONE);
        noDataV.setVisibility(hasData ? View.GONE : View.VISIBLE);
    }

    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.getHospital(false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return hasLoadedAllItems;
                }
            };

            mPaginate = Paginate.with(storesRV, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }


    @Override
    public void onRefresh() {
        mPresenter.getHospital(true);
    }


    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        for (int i = 0; i < mAdapter.getInfos().size(); i++) {
            mAdapter.getInfos().get(i).setCheck(i == position ? true : false);
        }
        mAdapter.notifyDataSetChanged();
        provideCache().put("choiceItem", position);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Cache getCache() {
        return provideCache();
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(storesRV);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
    }
}
