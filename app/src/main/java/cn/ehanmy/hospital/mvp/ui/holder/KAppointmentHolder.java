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
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter;

import static cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter.ViewName.CANCEL;
import static cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter.ViewName.CHANGE_APPOINTMENT;
import static cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter.ViewName.HUAKOU;
import static cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter.ViewName.INFO;
import static cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter.ViewName.ITEM;
import static cn.ehanmy.hospital.mvp.ui.adapter.KAppointmentAdapter.ViewName.OK;


/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class KAppointmentHolder extends BaseHolder<OrderProjectDetailBean> {

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.order_id)
    TextView order_id;
    @BindView(R.id.statusDesc)
    TextView statusDescTV;

    @BindView(R.id.change)
    View change;
    @BindView(R.id.cancel)
    View cancel;
    @BindView(R.id.info)
    View info;
    @BindView(R.id.ok)
    TextView okV;

    @BindView(R.id.order_project)
    TextView order_project;
    @BindView(R.id.order_phone)
    TextView order_phone;
    @BindView(R.id.count)
    TextView countTV;
    @BindView(R.id.surplusNum)
    TextView surplusNumTV;
    @BindView(R.id.time)
    TextView order_time;

    private KAppointmentAdapter.OnChildItemClickLinstener onChildItemClickLinstener;

    public KAppointmentHolder(View itemView, KAppointmentAdapter.OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        change.setOnClickListener(this);
        cancel.setOnClickListener(this);
        okV.setOnClickListener(this);
        info.setOnClickListener(this);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public void setData(OrderProjectDetailBean order, int position) {

        line1.setVisibility(View.VISIBLE);
        line2.setBackgroundColor(Color.WHITE);
        order_id.setText(order.getReservationId());
        order_phone.setText(order.getPhone());
        order_time.setText(order.getReservationDate());
        surplusNumTV.setText(order.getSurplusNum() + "");
        statusDescTV.setText(order.getReservationStatusDesc());
        order_project.setText(order.getGoods().getName());
        order_project.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        String status = order.getReservationStatus();
        switch (status) {
            case "0":
                okV.setText("确认");
                okV.setTag(status);
                info.setVisibility(View.VISIBLE);
                okV.setVisibility(View.VISIBLE);
                change.setVisibility(View.VISIBLE);
                cancel.setVisibility(View.VISIBLE);
                break;
            case "1":
                okV.setTag(status);
                okV.setText("划扣");
                info.setVisibility(View.VISIBLE);
                okV.setVisibility(View.VISIBLE);
                change.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                break;
            case "2":
                info.setVisibility(View.VISIBLE);
                okV.setVisibility(View.GONE);
                change.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                break;
            case "3":
                info.setVisibility(View.VISIBLE);
                okV.setVisibility(View.GONE);
                change.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        if (null != onChildItemClickLinstener) {
            switch (view.getId()) {
                case R.id.change:
                    onChildItemClickLinstener.onChildItemClick(view, CHANGE_APPOINTMENT, getAdapterPosition());
                    return;
                case R.id.cancel:
                    onChildItemClickLinstener.onChildItemClick(view, CANCEL, getAdapterPosition());
                    return;
                case R.id.info:
                    onChildItemClickLinstener.onChildItemClick(view, INFO, getAdapterPosition());
                    return;
                case R.id.ok:
                    onChildItemClickLinstener.onChildItemClick(view, "1".equals(view.getTag()) ? HUAKOU : OK, getAdapterPosition());
                    return;
            }
        }
        onChildItemClickLinstener.onChildItemClick(view, ITEM, getAdapterPosition());
    }

    @Override
    protected void onRelease() {
        this.order_id = null;
        this.order_phone = null;
        this.order_project = null;
        this.statusDescTV = null;
        this.surplusNumTV = null;
        this.order_time = null;
        this.countTV = null;
        this.change = null;
        this.cancel = null;
        this.info = null;
        this.line1 = null;
        this.okV = null;
        this.line2 = null;
    }

}
