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

import android.view.View;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;


/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class KAppointmentTitleHolder extends BaseHolder<OrderProjectDetailBean> {

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;

    public KAppointmentTitleHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(OrderProjectDetailBean order, int position) {
        line1.setVisibility(View.GONE);
        line2.setBackgroundResource(R.drawable.order_list_title_back);
    }

    @Override
    protected void onRelease() {
        this.line1 = null;
        this.line2 = null;
    }
}
