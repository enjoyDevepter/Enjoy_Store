package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.Order;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.CancelShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.CancelShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.ConfirmShopAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.shop_appointment.GetShopAppointmentPageResponse;
import io.reactivex.Observable;


public interface ShopAppointmentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void startLoadMore();
        void endLoadMore();
        void setEnd(boolean isEnd);
        Activity getActivity();
        void showError(boolean hasDate);
        Cache getCache();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<GetShopAppointmentPageResponse> getShopAppointmentPage(GetShopAppointmentPageRequest request);
        Observable<CancelShopAppointmentResponse> cancelShopAppointment(CancelShopAppointmentRequest request);
        Observable<ConfirmShopAppointmentResponse> confirmShopAppointmentResponseObservable(ConfirmShopAppointmentRequest request);
    }
}
