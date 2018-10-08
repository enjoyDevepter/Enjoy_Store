package cn.ehanmy.hospital.mvp.ui.holder;


import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.activity.ActivityInfoBean;

import static cn.ehanmy.hospital.mvp.ui.holder.ActivityInfoListHolder.ViewName.DELETE;
import static cn.ehanmy.hospital.mvp.ui.holder.ActivityInfoListHolder.ViewName.EDIT;

public class ActivityInfoListHolder extends BaseHolder<ActivityInfoBean> {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.content)
    TextView content;

    @BindView(R.id.edit)
    View edit;
    @BindView(R.id.delete)
    View delete;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;


    private OnChildItemClickLinstener onChildItemClickLinstener;

    public ActivityInfoListHolder(View itemView, OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public void setData(ActivityInfoBean data, int position) {

        name.setText(data.getTitle());
        content.setText(Html.fromHtml(data.getContent()));
        image.setImageResource(R.mipmap.default_image);
        mImageLoader.loadImage(itemView.getContext(),
                ImageConfigImpl
                        .builder()
                        .placeholder(R.drawable.place_holder_img)
                        .url(data.getImage())
                        .imageView(image)
                        .build());
    }

    @Override
    protected void onRelease() {
        this.name = null;
        this.content = null;
        this.image = null;
        this.edit = null;
        this.delete = null;
    }


    @Override
    public void onClick(View view) {
        if (null != onChildItemClickLinstener) {
            switch (view.getId()) {
                case R.id.edit:
                    onChildItemClickLinstener.onChildItemClick(view, EDIT, getAdapterPosition());
                    return;
                case R.id.delete:
                    onChildItemClickLinstener.onChildItemClick(view, DELETE, getAdapterPosition());
                    return;
            }
        }
        super.onClick(view);
    }


    public interface OnChildItemClickLinstener {
        void onChildItemClick(View v, ViewName viewname, int position);
    }

    public enum ViewName {
        EDIT,
        DELETE,
    }

}


