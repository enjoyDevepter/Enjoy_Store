package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.GoodsConfirmWithSpecRequest;
import cn.ehanmy.hospital.mvp.model.entity.request.HGoodsConfirmRequest;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import io.reactivex.Observable;


public interface HGoodsConfirmContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();

        Cache getCache();

        void updateUI(GoodsConfirmResponse response);

        void payOk();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<GoodsConfirmResponse> confirmGoods(GoodsConfirmRequest request);

        Observable<GoodsConfirmResponse> confirmGoodsWithSpec(GoodsConfirmWithSpecRequest request);

        Observable<BaseResponse> makeAppointment(HGoodsConfirmRequest request);

    }
}
