package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerProjectSettingComponent;
import cn.ehanmy.hospital.di.module.ProjectSettingModule;
import cn.ehanmy.hospital.mvp.contract.ProjectSettingContract;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Category;
import cn.ehanmy.hospital.mvp.model.entity.user.MerchBean;
import cn.ehanmy.hospital.mvp.presenter.ProjectSettingPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ProjectSettingActivity extends BaseActivity<ProjectSettingPresenter> implements ProjectSettingContract.View, View.OnClickListener {

    private static final int ITEM_TYPE_SELECT = 1;
    private static final int ITEM_TYPE_NORMAL = 0;
    @BindView(R.id.back)
    View backV;
    @BindView(R.id.title)
    TextView titleTV;
    @BindView(R.id.finish)
    View finishV;
    @BindView(R.id.list1)
    RecyclerView list1;
    @BindView(R.id.list2)
    RecyclerView list2;
    @BindView(R.id.list3)
    RecyclerView list3;
    @BindView(R.id.list4)
    RecyclerView list4;
    private int currentIndex1 = 0;
    // 保存2级列表的选中状态
    private int currentIndex2 = 0;
    private Map<Integer,Integer> currentIndex3 = new HashMap<>();

    /**保存当前被选中的商品*/
    private Set<String> currentIndex4 = new HashSet<>();

    private List<Category> categoryList1 = new ArrayList<>();
    private List1Adapter list1Adapter = new List1Adapter();
    private List2Adapter list2Adapter = new List2Adapter();
    private List3Adapter list3Adapter = new List3Adapter();
    private List4Adapter list4Adapter = new List4Adapter();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerProjectSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .projectSettingModule(new ProjectSettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_project_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        finishV.setOnClickListener(this);
        backV.setOnClickListener(this);
        titleTV.setText("项目设置");
        list1.setLayoutManager(new LinearLayoutManager(this));
        list2.setLayoutManager(new LinearLayoutManager(this));
        list3.setLayoutManager(new LinearLayoutManager(this));
        list4.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    public void updateCategory(List<Category> categoryList, List<String> selectList) {
        currentIndex4.clear();
        if(selectList != null && selectList.size() != 0){
            currentIndex4.addAll(selectList);
        }
        categoryList1.clear();
        categoryList1.addAll(categoryList);
        list1.setAdapter(list1Adapter);
        list2.setAdapter(list2Adapter);
        list3.setAdapter(list3Adapter);

        Category category1 = categoryList1.get(currentIndex1);
        Integer key = currentIndex2;
        Category category2 = category1.getGoodsCategoryList().get(0);
        Category category3 = category2.getGoodsCategoryList().get(0);
        mPresenter.getGoodsList(category3.getCategoryId(),category2.getCategoryId());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                killMyself();
                break;
            case R.id.finish:
                List<String> list = new ArrayList<>();
                list.addAll(currentIndex4);
                mPresenter.setProjectSetting(list);
                break;
        }
    }

    /**保存当前分类下的商品*/
    private List<MerchBean> merchBeanList = new ArrayList<>();
    public void updateGoodsList(List<MerchBean> lists){
        merchBeanList.clear();
        if(lists != null && lists.size() != 0){
            merchBeanList.addAll(lists);
        }
        list4.setAdapter(list4Adapter);
    }

    private class ListHolder extends RecyclerView.ViewHolder {

        TextView content;

        public ListHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
        }
    }

    private class List1Adapter extends RecyclerView.Adapter<ListHolder> {

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutId = R.layout.project_setting_normal_item;
            if (viewType == ITEM_TYPE_SELECT) {
                layoutId = R.layout.project_setting_selected_item;
            }
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            inflate.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ArmsUtils.dip2px(ArmsUtils.getContext(), 30)));
            return new ListHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            TextView content = holder.content;
            content.setText(categoryList1.get(position).getName());
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex1 = position;
                    list1.setAdapter(list1Adapter);
                    list2.setAdapter(list2Adapter);
                    list3.setAdapter(list3Adapter);
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return position == currentIndex1 ? ITEM_TYPE_SELECT : ITEM_TYPE_NORMAL;
        }

        @Override
        public int getItemCount() {
            return categoryList1.size();
        }
    }

    private class List2Adapter extends RecyclerView.Adapter<ListHolder> {

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutId = R.layout.project_setting_normal_item;
            if (viewType == ITEM_TYPE_SELECT) {
                layoutId = R.layout.project_setting_selected_item;
            }
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            inflate.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ArmsUtils.dip2px(ArmsUtils.getContext(), 30)));
            return new ListHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            TextView content = holder.content;
            content.setText(getList().get(position).getName());
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex2 = position;
                    if(!currentIndex3.containsKey(currentIndex2)){
                        currentIndex3.put(currentIndex2,0);
                    }
                    list2.setAdapter(list2Adapter);
                    list3.setAdapter(list3Adapter);
                    Category category1 = categoryList1.get(currentIndex1);
                    Integer key = currentIndex2;
                    Category category2 = category1.getGoodsCategoryList().get(key);
                    Category category3 = category2.getGoodsCategoryList().get(0);
                    mPresenter.getGoodsList(category3.getCategoryId(),category2.getCategoryId());
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return position == currentIndex2 ? ITEM_TYPE_SELECT : ITEM_TYPE_NORMAL;
        }

        @Override
        public int getItemCount() {
            return getList().size();
        }

        private List<Category> getList() {
            return categoryList1.get(currentIndex1).getGoodsCategoryList();
        }
    }

    private class List3Adapter extends RecyclerView.Adapter<ListHolder> {

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutId = R.layout.project_setting_normal_item;
            if (viewType == ITEM_TYPE_SELECT) {
                layoutId = R.layout.project_setting_selected_item;
            }
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            inflate.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ArmsUtils.dip2px(ArmsUtils.getContext(), 30)));
            return new ListHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            TextView content = holder.content;
            Integer key = currentIndex2;
            Category category1 = categoryList1.get(currentIndex1);
            Category category2 = category1.getGoodsCategoryList().get(key);
            Category category3 = category2.getGoodsCategoryList().get(position);
            content.setText(category3.getName());
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex3.put(currentIndex2,position);
                    list3.setAdapter(list3Adapter);
                    mPresenter.getGoodsList(category3.getCategoryId(),category2.getCategoryId());
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            if(!currentIndex3.containsKey(currentIndex2)){
                currentIndex3.put(currentIndex2,0);
            }
            int integers = currentIndex3.get(currentIndex2);
            return integers == position ? ITEM_TYPE_SELECT : ITEM_TYPE_NORMAL;

        }

        @Override
        public int getItemCount() {
            return categoryList1.get(currentIndex1).getGoodsCategoryList().get(currentIndex2).getGoodsCategoryList().size();
        }
    }

    private class List4Adapter extends RecyclerView.Adapter<ListHolder> {

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutId = R.layout.project_setting_normal_item;
            if (viewType == ITEM_TYPE_SELECT) {
                layoutId = R.layout.project_setting_choose_item;
            }
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            inflate.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ListHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            TextView content = holder.content;
            MerchBean mb = merchBeanList.get(position);
            content.setText(mb.getName());
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentIndex4.contains(mb.getMerchId())) {
                        currentIndex4.remove(mb.getMerchId());
                    } else {
                        currentIndex4.add(mb.getMerchId());
                    }
                    if(list4.getAdapter() == null){
                        list4.setAdapter(list4Adapter);
                    }else{
                        list4Adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            MerchBean merchBean = merchBeanList.get(position);
            if (!currentIndex4.contains(merchBean.getMerchId())) {
                return ITEM_TYPE_NORMAL;
            }
            return ITEM_TYPE_SELECT;
        }

        @Override
        public int getItemCount() {
            return merchBeanList.size();
        }
    }}
