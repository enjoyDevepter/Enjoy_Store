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

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import java.io.File;

import butterknife.BindView;
import cn.ehanmy.hospital.R;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class DiaryUpdateImageHolder extends BaseHolder<String> {
    @BindView(R.id.image)
    ImageView imageIV;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;

    public DiaryUpdateImageHolder(View itemView) {
        super(itemView);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(String path, int position) {
        if(TextUtils.isEmpty(path)){
            return;
        }
        File file = new File(path);
        // 如果给定的字符串表示路径，且文件存在，则将文件解析为图片
        if(file.exists()){
            imageIV.setImageBitmap(BitmapFactory.decodeFile(path));
        }else{
            // 否则，path可能是一个图片的url
            mImageLoader.loadImage(itemView.getContext(),
                    ImageConfigImpl
                            .builder()
                            .placeholder(R.drawable.place_holder_img)
                            .url(path)
                            .imageView(imageIV)
                            .build());

        }
    }

    /**
     * 在 Activity 的 onDestroy 中使用 {@link DefaultAdapter#releaseAllHolder(RecyclerView)} 方法 (super.onDestroy() 之前)
     * {@link BaseHolder#onRelease()} 才会被调用, 可以在此方法中释放一些资源
     */
    @Override
    protected void onRelease() {
        this.imageIV = null;
    }
}
