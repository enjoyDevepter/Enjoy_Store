/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ehanmy.hospital.mvp.ui.holder;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderMemberInfoBean;
import cn.ehanmy.hospital.mvp.ui.adapter.OrderCenterListAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.MoneyView;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class OrderCenterListTitleHolder extends BaseHolder<OrderBean> {

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;

    @BindView(R.id.order_project)
    TextView order_project;
    @BindView(R.id.order_phone)
    TextView order_phone;

    @BindView(R.id.order_price_title)
    View order_price_title;
    @BindView(R.id.order_price)
    View order_price;

    @BindView(R.id.order_secend_parent)
    View order_secend_parent;
    @BindView(R.id.order_secend_price_title)
    View order_secend_price_title;
    @BindView(R.id.order_secend_price)
    View order_secend_price;

    @BindView(R.id.order_time)
    TextView order_time;

    private OrderCenterListAdapter.OnChildItemClickLinstener onChildItemClickLinstener;

    public OrderCenterListTitleHolder(View itemView, OrderCenterListAdapter.OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public void setData(OrderBean order, int position) {
        line1.setVisibility(View.GONE);
        line2.setBackgroundResource(R.drawable.order_list_title_back);
        order_project.setText("项目");
        order_project.setGravity(Gravity.CENTER);
        order_phone.setText("手机");
        order_price_title.setVisibility(View.VISIBLE);
        order_price.setVisibility(View.GONE);
        order_secend_price_title.setVisibility(View.VISIBLE);
        order_secend_price.setVisibility(View.GONE);
        order_time.setText("时间");

        String orderListStatus = order.getOrderListStatus();
        if (TextUtils.isEmpty(orderListStatus)) {
            order_secend_parent.setVisibility(View.VISIBLE);
        } else {
            switch (orderListStatus) {
                case "1":
                    order_secend_parent.setVisibility(View.GONE);
                    break;
                case "2":
                    order_secend_parent.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    order_secend_parent.setVisibility(View.GONE);
                    break;
            }
        }

    }

    @Override
    protected void onRelease() {
        this.line1 = null;
        this.line2 = null;
        this.order_project = null;
        this.order_phone = null;
        this.order_price_title = null;
        this.order_price = null;
        this.order_secend_parent = null;
        this.order_secend_price_title = null;
        this.order_secend_price = null;
        this.order_time = null;
    }
}

