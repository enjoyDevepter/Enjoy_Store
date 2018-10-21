package cn.ehanmy.hospital.mvp.ui.adapter;

import android.view.View;
import android.widget.AdapterView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberMiniInfoBean;
import cn.ehanmy.hospital.mvp.ui.holder.MemberInfoListHolder;

public class MemberInfoListAdapter extends DefaultAdapter<MemberMiniInfoBean> {

    public MemberInfoListAdapter(List<MemberMiniInfoBean> infos) {
        super(infos);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private AdapterView.OnItemClickListener onItemClickListener;

    @Override
    public BaseHolder<MemberMiniInfoBean> getHolder(View v, int viewType) {
        return new MemberInfoListHolder(v, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(parent,view,position,id);
                }
            }
        });
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.member_info_item;
    }
}
