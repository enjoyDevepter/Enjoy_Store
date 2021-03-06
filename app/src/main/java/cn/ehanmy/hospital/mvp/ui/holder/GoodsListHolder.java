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
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Goods;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsListAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.MoneyView;
import io.reactivex.Observable;

public class GoodsListHolder extends BaseHolder<Goods> {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.count_title)
    View countTagV;
    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.price)
    MoneyView priceMV;
    @BindView(R.id.buy)
    TextView buy;

    boolean isBuy;
    GoodsListAdapter.OnChildItemClickLinstener onChildItemClickLinstener;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架


    public GoodsListHolder(View itemView, boolean isBuy, GoodsListAdapter.OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        this.isBuy = isBuy;
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }


    @Override
    public void setData(Goods data, int position) {
        mImageLoader.loadImage(itemView.getContext(),
                ImageConfigImpl
                        .builder()
                        .placeholder(R.drawable.place_holder_img)
                        .url(data.getImage())
                        .isCenterCrop(true)
                        .imageView(image)
                        .build());
        Observable.just(data.getName() + data.getTitle())
                .subscribe(s -> title.setText(s));
        Observable.just(data.getSales())
                .subscribe(s -> count.setText(String.valueOf(s)));
        Observable.just(data.getSalePrice())
                .subscribe(s -> priceMV.setMoneyText(String.valueOf(s)));
        buy.setOnClickListener(this);
        buy.setText(isBuy ? "立即下单" : "立即预约");
        countTagV.setVisibility(isBuy ? View.VISIBLE : View.INVISIBLE);
        count.setVisibility(isBuy ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buy:
                onChildItemClickLinstener.onChildItemClick(view, GoodsListAdapter.ViewName.BUY, getAdapterPosition());
                return;
        }
        onChildItemClickLinstener.onChildItemClick(view, GoodsListAdapter.ViewName.ITEM, getAdapterPosition());
    }

    @Override
    protected void onRelease() {
        //只要传入的 Context 为 Activity, Glide 就会自己做好生命周期的管理, 其实在上面的代码中传入的 Context 就是 Activity
        //所以在 onRelease 方法中不做 clear 也是可以的, 但是在这里想展示一下 clear 的用法
        mImageLoader.clear(mAppComponent.application(), ImageConfigImpl.builder()
                .imageViews(image)
                .build());
        this.image = null;
        this.title = null;
        this.countTagV = null;
        this.count = null;
        this.priceMV = null;
        this.buy = null;
    }
}
