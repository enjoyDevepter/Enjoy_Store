package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import cn.ehanmy.hospital.mvp.model.entity.QiniuRequest;
import cn.ehanmy.hospital.mvp.model.entity.QiniuResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.AddActivityResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.ChangeActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.ChangeActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;


public interface ActivityAddContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<AddActivityResponse> addActivity(AddActivityRequest request);
        Observable<ChangeActivityInfoResponse> changeActivityInfo(ChangeActivityInfoRequest request);
        Observable<QiniuResponse> getQiniuInfo(QiniuRequest request);
    }
}
