package cn.ehanmy.hospital.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;

public class OrderInfoBuyInfoHolder extends BaseHolder<GoodsOrderBean> {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;


    public OrderInfoBuyInfoHolder(View itemView) {
        super(itemView);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(GoodsOrderBean data, int position) {

        mImageLoader.loadImage(itemView.getContext(),
                ImageConfigImpl
                        .builder()
                        .url(data.getImage())
                        .imageView(image)
                        .build());

        title.setText(data.getName());
        time.setText(data.getReservationDate());
    }

    @Override
    protected void onRelease() {
        this.image = null;
        this.title = null;
        this.time = null;
    }
}

