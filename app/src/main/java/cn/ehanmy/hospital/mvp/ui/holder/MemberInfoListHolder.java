package cn.ehanmy.hospital.mvp.ui.holder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberMiniInfoBean;

public class MemberInfoListHolder extends BaseHolder<MemberMiniInfoBean> {


    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.btn)
    View btn;

    private AdapterView.OnItemClickListener onItemClickListener;

    public MemberInfoListHolder(View itemView, AdapterView.OnItemClickListener onItemClickListener) {
        super(itemView);
        this.onItemClickListener = onItemClickListener;
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MemberMiniInfoBean data, int position) {

        mImageLoader.loadImage(itemView.getContext(),
                ImageConfigImpl
                        .builder()
                        .placeholder(R.drawable.place_holder_img)
                        .url(data.getHeadImage())
                        .imageView(image)
                        .build());

        name.setText(data.getNickName());
        phone.setText(data.getMobile());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null){
                    onItemClickListener.onItemClick(null,btn,position,btn.getId());
                }
            }
        });
    }

    @Override
    protected void onRelease() {
        this.image = null;
        this.name = null;
        this.phone = null;
        this.btn = null;
    }
}

