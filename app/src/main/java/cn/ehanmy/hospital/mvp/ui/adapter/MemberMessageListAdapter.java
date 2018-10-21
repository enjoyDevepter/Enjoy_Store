package cn.ehanmy.hospital.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberMessageBean;
import cn.ehanmy.hospital.mvp.ui.holder.MemberMessageListHolder;

public class MemberMessageListAdapter extends DefaultAdapter<MemberMessageBean> {

    public MemberMessageListAdapter(List<MemberMessageBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<MemberMessageBean> getHolder(View v, int viewType) {
        return new MemberMessageListHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.member_info_message_list_item;
    }
}

