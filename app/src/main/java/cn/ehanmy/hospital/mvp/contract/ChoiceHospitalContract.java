package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import cn.ehanmy.hospital.mvp.model.entity.hospital.AllAddressResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalListRequest;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitalListResponse;
import cn.ehanmy.hospital.mvp.model.entity.hospital.SimpleRequest;
import io.reactivex.Observable;


public interface ChoiceHospitalContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();

        Cache getCache();

        void showConent(boolean hasData);

        void startLoadMore();

        void endLoadMore();

        void setLoadedAllItems(boolean has);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<AllAddressResponse> getAllAddressList(SimpleRequest request);

        Observable<HospitalListResponse> getHospitals(HospitalListRequest request);
    }
}
