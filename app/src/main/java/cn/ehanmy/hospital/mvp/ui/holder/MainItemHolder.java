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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.MainItem;
import io.reactivex.Observable;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class MainItemHolder extends BaseHolder<MainItem> {

    @BindView(R.id.image)
    View mImage;
    @BindView(R.id.name)
    TextView mName;

    public MainItemHolder(View itemView) {
        super(itemView);
        int screenWidth = ArmsUtils.getScreenWidth(ArmsUtils.getContext());
        itemView.setLayoutParams(new RecyclerView.LayoutParams(screenWidth / 4, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setData(MainItem data, int position) {
        Observable.just(data.getName())
                .subscribe(s -> mName.setText(s));
        Observable.just(data.getImageId())
                .subscribe(s -> mImage.setBackgroundResource(s));
    }

    @Override
    protected void onRelease() {
        this.mImage = null;
        this.mName = null;
    }
}
