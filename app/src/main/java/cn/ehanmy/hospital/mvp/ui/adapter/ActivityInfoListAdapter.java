package cn.ehanmy.hospital.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.activity.ActivityInfoBean;
import cn.ehanmy.hospital.mvp.ui.holder.ActivityInfoListHolder;
import cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentHolder;

public class ActivityInfoListAdapter extends DefaultAdapter<ActivityInfoBean> {
    public ActivityInfoListAdapter(List<ActivityInfoBean> infos) {
        super(infos);
    }


    public void setOnChildItemClickLinstener(ActivityInfoListHolder.OnChildItemClickLinstener onChildItemClickLinstener) {
        this.onChildItemClickLinstener = onChildItemClickLinstener;
        setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {

            }
        });
    }

    private ActivityInfoListHolder.OnChildItemClickLinstener onChildItemClickLinstener;

    @Override
    public BaseHolder<ActivityInfoBean> getHolder(View v, int viewType) {
        return new ActivityInfoListHolder(v, new ActivityInfoListHolder.OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, ActivityInfoListHolder.ViewName viewname, int position) {
                if (onChildItemClickLinstener != null) {
                    onChildItemClickLinstener.onChildItemClick(v, viewname, position);
                }
            }
        });
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_info_list_item;
    }


    public ActivityInfoListHolder.OnChildItemClickLinstener getOnChildItemClickLinstener() {
        return onChildItemClickLinstener;
    }
}
