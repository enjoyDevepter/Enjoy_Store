package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import cn.ehanmy.hospital.mvp.model.entity.order.GetBuyInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetBuyInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderInfoResponse;
import io.reactivex.Observable;


public interface OrderInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();
        void setEnd(boolean isEnd);
        void showError(boolean hasDate);
        void startLoadMore();
        void endLoadMore();
        Cache getCache();
        void updateOrderInfo(OrderInfoBean orderInfoBean);
        void updateRecyclerViewHeight();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<OrderInfoResponse> requestOrderInfo(OrderInfoRequest request);
        Observable<GetBuyInfoResponse> getBuyInfo(GetBuyInfoRequest request);
    }
}
