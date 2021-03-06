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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Category;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GoodsFilterThirdHolder extends BaseHolder<Category> {

    @BindView(R.id.name)
    TextView nameTV;
    @BindView(R.id.choice)
    View choiceV;


    public GoodsFilterThirdHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Category category, int position) {
        Observable.just(category.getName()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                nameTV.setText(category.getName());
            }
        });
        Observable.just(category.isChoice()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean choice) throws Exception {
                choiceV.setVisibility(choice ? View.VISIBLE : View.INVISIBLE);
                nameTV.setTextColor(choice ? Color.parseColor("#FF5FBFE3") : Color.parseColor("#FF000000"));
            }
        });
    }


    /**
     * 在 Activity 的 onDestroy 中使用 {@link DefaultAdapter#releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link BaseHolder#onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    @Override
    protected void onRelease() {
        this.nameTV = null;
        this.choiceV = null;
    }
}
