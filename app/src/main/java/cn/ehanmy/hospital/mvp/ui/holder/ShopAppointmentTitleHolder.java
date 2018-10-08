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
public class ShopAppointmentTitleHolder extends BaseHolder<OrderProjectDetailBean> {

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

    public ShopAppointmentTitleHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(OrderProjectDetailBean order, int position) {
        line1.setVisibility(View.GONE);
        line2.setBackgroundResource(R.drawable.order_list_title_back);
        order_project.setText("项目");
        order_phone.setText("手机");
        order_related.setText("关联");
        order_status.setText("状态");
        order_time.setText("预约时间");
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
