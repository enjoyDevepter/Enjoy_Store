package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import cn.ehanmy.hospital.mvp.model.entity.activity.DeleteActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.DeleteActivityInfoResponse;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoRequest;
import cn.ehanmy.hospital.mvp.model.entity.activity.GetActivityInfoResponse;
import io.reactivex.Observable;


public interface ActivityInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setEnd(boolean isEnd);
        void showError(boolean hasDate);
        Activity getActivity();
        void startLoadMore();
        void endLoadMore();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<GetActivityInfoResponse> getActivityInfo(GetActivityInfoRequest request);
        Observable<DeleteActivityInfoResponse> deleteActivityInfo(DeleteActivityInfoRequest request);
    }
}
