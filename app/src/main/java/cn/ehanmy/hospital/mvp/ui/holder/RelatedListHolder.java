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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.RelatedOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;

import static cn.ehanmy.hospital.mvp.ui.holder.RelatedListHolder.ViewName.RELATED;
import static cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentHolder.ViewName.CANCEL;
import static cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentHolder.ViewName.CHANGE_APPOINTMENT;
import static cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentHolder.ViewName.HUAKOU;
import static cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentHolder.ViewName.INFO;
import static cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentHolder.ViewName.OK;


/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class RelatedListHolder extends BaseHolder<RelatedOrderBean> {

    @BindView(R.id.order_id)
    TextView order_id;
    @BindView(R.id.order_phone)
    TextView order_phone;
    @BindView(R.id.order_project)
    TextView order_project;
    @BindView(R.id.money)
    TextView money;
    @BindView(R.id.order_time)
    TextView order_time;

    @BindView(R.id.related)
    View related;
    @BindView(R.id.button_group)
    View button_group;

    @BindView(R.id.parent)
    View parent;
    @BindView(R.id.btn_group_title)
    View btn_group_title;

    private OnChildItemClickLinstener onChildItemClickLinstener;

    public RelatedListHolder(View itemView, OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        related.setOnClickListener(this);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public void setData(RelatedOrderBean order, int position) {
        if (position == 0) {
            order_id.setText("编号");
            order_phone.setText("手机");
            order_project.setText("项目");
            money.setText("金额");
            order_time.setText("时间");
            btn_group_title.setVisibility(View.VISIBLE);
            button_group.setVisibility(View.GONE);
            parent.setBackgroundColor(Color.parseColor("#E4E4E4"));
        } else {
            btn_group_title.setVisibility(View.GONE);
            parent.setBackgroundColor(Color.parseColor("#FFFFFF"));
            button_group.setVisibility(View.VISIBLE);
            order_id.setText(order.getOrderId());
            order_phone.setText(order.getMember().getMobile());
            order_time.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(order.getOrderTime())));
            money.setText(String.format("¥%.2f",(order.getTotalPrice()/100.0)));
            order_project.setText(order.getGoods().getName());

            related.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (null != onChildItemClickLinstener) {
            switch (view.getId()) {
                case R.id.related:
                    onChildItemClickLinstener.onChildItemClick(view, RELATED, getAdapterPosition());
                    return;
            }
        }
        super.onClick(view);
    }

    public interface OnChildItemClickLinstener {
        void onChildItemClick(View v, ViewName viewname, int position);
    }

    public enum ViewName {
        RELATED
    }

    @Override
    protected void onRelease() {
        this.order_id = null;
        this.order_phone = null;
        this.order_project = null;
        this.related = null;
        this.order_time = null;
        this.button_group = null;
    }
}
