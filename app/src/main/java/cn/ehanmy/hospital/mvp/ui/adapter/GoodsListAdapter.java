package cn.ehanmy.hospital.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.ui.holder.GoodsListHolder;

public class GoodsListAdapter extends DefaultAdapter<GoodsListBean> {

    private OnChildItemClickLinstener onChildItemClickLinstener;

    public GoodsListAdapter(List<GoodsListBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GoodsListBean> getHolder(View v, int viewType) {
        return new GoodsListHolder(v, new OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, ViewName viewname, int position) {
                if (onChildItemClickLinstener != null) {
                    onChildItemClickLinstener.onChildItemClick(v, viewname, position);
                }
            }
        });
    }

    public void setOnChildItemClickLinstener(OnChildItemClickLinstener onChildItemClickLinstener) {
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.goods_list_item;
    }

    public enum ViewName {
        BUY, ITEM
    }

    public interface OnChildItemClickLinstener {
        void onChildItemClick(View v, ViewName viewname, int position);
    }
}
