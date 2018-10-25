package cn.ehanmy.hospital.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.ui.holder.OrderInfoBuyInfoHolder;

public class OrderInfoBuyInfoAdapter extends DefaultAdapter<GoodsOrderBean> {
    public OrderInfoBuyInfoAdapter(List<GoodsOrderBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GoodsOrderBean> getHolder(View v, int viewType) {
        return new OrderInfoBuyInfoHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.order_info_buy_info_item;
    }
}
