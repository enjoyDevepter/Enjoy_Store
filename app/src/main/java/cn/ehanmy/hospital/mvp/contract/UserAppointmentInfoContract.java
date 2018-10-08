package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;
import io.reactivex.Observable;


public interface UserAppointmentInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();
        void updateOrderInfo(OrderProjectDetailBean orderInfoBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<GetUserAppointmentInfoResponse> getUserAppointmentInfo(GetUserAppointmentInfoRequest request);
    }
}
