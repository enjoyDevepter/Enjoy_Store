package cn.ehanmy.hospital.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberMessageBean;

public class MemberMessageListHolder extends BaseHolder<MemberMessageBean> {

    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.time_message)
    TextView time_message;
    @BindView(R.id.repeat)
    TextView repeat;
    @BindView(R.id.time_repeat)
    TextView time_repeat;

    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;


    public MemberMessageListHolder(View itemView) {
        super(itemView);
        mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(MemberMessageBean data, int position) {
        content.setText(data.getContent());
        repeat.setText(data.getReplyContent());
        time_message.setText(data.getSendTime());
        time_repeat.setText(data.getReplyTime());
    }

    @Override
    protected void onRelease() {
        this.content = null;
        this.repeat = null;
        this.time_message = null;
        this.time_repeat = null;
    }
}
