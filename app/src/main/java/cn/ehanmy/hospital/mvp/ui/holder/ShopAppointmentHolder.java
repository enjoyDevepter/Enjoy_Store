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
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.UserAppointmentGoodsBean;

import static cn.ehanmy.hospital.mvp.ui.holder.ShopAppointmentHolder.ViewName.CANCEL;
import static cn.ehanmy.hospital.mvp.ui.holder.ShopAppointmentHolder.ViewName.INFO;
import static cn.ehanmy.hospital.mvp.ui.holder.ShopAppointmentHolder.ViewName.RELATED;


/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ShopAppointmentHolder extends BaseHolder<OrderProjectDetailBean> {

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.order_id)
    TextView order_id;

    @BindView(R.id.info)
    View info;
    @BindView(R.id.related)
    View related;
    @BindView(R.id.cancel)
    View cancel;
    @BindView(R.id.line2)
    View line2;

    @BindView(R.id.order_project)
    TextView order_project;
    @BindView(R.id.order_phone)
    TextView order_phone;
    @BindView(R.id.order_related)
    TextView order_related;
    @BindView(R.id.order_status)
    TextView order_status;
    @BindView(R.id.order_time)
    TextView order_time;

    private OnChildItemClickLinstener onChildItemClickLinstener;

    public ShopAppointmentHolder(View itemView, OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        cancel.setOnClickListener(this);
        info.setOnClickListener(this);
        related.setOnClickListener(this);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public void setData(OrderProjectDetailBean order, int position) {
        line1.setVisibility(View.VISIBLE);
        line2.setBackgroundColor(Color.WHITE);
        order_id.setText(order.getReservationId());
        order_phone.setText(order.getPhone());
        order_related.setText(order.getStatusDesc());
        order_time.setText(order.getReservationDate());
        order_status.setText(order.getRelateStatusDesc());
        UserAppointmentGoodsBean goods = order.getGoods();
        if(goods != null){
            order_project.setText(goods.getName());
        }

        cancel.setVisibility(View.GONE);
        related.setVisibility(View.GONE);
        info.setVisibility(View.GONE);

        String status = order.getReservationStatus();
        if (!TextUtils.isEmpty(status)) {
            switch (status) {
                case "0":
                    info.setVisibility(View.VISIBLE);
                    related.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    info.setVisibility(View.VISIBLE);
                    break;
                case "3":
                    info.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (null != onChildItemClickLinstener) {
            switch (view.getId()) {
                case R.id.info:
                    onChildItemClickLinstener.onChildItemClick(view, INFO, getAdapterPosition());
                    return;
                case R.id.related:
                    onChildItemClickLinstener.onChildItemClick(view, RELATED, getAdapterPosition());
                    return;
                case R.id.cancel:
                    onChildItemClickLinstener.onChildItemClick(view, CANCEL, getAdapterPosition());
                    return;
            }
        }
        super.onClick(view);
    }

    public interface OnChildItemClickLinstener {
        void onChildItemClick(View v, ViewName viewname, int position);
    }

    public enum ViewName {
        CANCEL,
        RELATED,
        INFO,
    }

    @Override
    protected void onRelease() {
        this.line1 = null;
        this.order_id = null;
        this.info = null;
        this.related = null;
        this.cancel = null;
        this.line2 = null;

        this.order_project = null;
        this.order_phone = null;
        this.order_related = null;
        this.order_status = null;
        this.order_time = null;
    }
}
