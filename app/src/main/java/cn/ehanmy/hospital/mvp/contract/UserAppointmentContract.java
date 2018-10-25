package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import cn.ehanmy.hospital.mvp.model.entity.user_appointment.CancelAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.CancelAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ConfirmAppointmentRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ConfirmAppointmentResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentPageResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.HuakouRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.HuakouResponse;
import io.reactivex.Observable;


public interface UserAppointmentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();

        Cache getCache();

        void startLoadMore();

        void endLoadMore();

        void showError(boolean hasDate);

        void setLoadedAllItems(boolean has);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<GetUserAppointmentPageResponse> getUserAppintmentPage(GetUserAppointmentPageRequest request);

        Observable<ConfirmAppointmentResponse> confirmAppointment(ConfirmAppointmentRequest request);

        Observable<CancelAppointmentResponse> cancelAppointment(CancelAppointmentRequest request);

        Observable<HuakouResponse> huakou(HuakouRequest request);
    }
}
