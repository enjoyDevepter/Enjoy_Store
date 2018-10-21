package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import cn.ehanmy.hospital.mvp.model.entity.reg.RegisterRequest;
import cn.ehanmy.hospital.mvp.model.entity.reg.RegisterResponse;
import cn.ehanmy.hospital.mvp.model.entity.reg.VeritfyRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import io.reactivex.Observable;


public interface RegisterContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showVerity();

        void hasRegister(boolean register);

        void inputUserInfo();

        void registerSuccess();

        Activity getActivity();

        Cache getCache();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<RegisterResponse> register(VeritfyRequest request);

        Observable<BaseResponse> register(RegisterRequest request);

        Observable<BaseResponse> getVerify(VeritfyRequest request);
    }
}
