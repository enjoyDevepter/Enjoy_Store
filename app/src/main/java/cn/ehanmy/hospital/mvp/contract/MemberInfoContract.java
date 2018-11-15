package cn.ehanmy.hospital.mvp.contract;

import android.app.Activity;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMessageListRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.GetMessageListResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberInfoByIdResponse;
import cn.ehanmy.hospital.mvp.model.entity.member_info.SendMessageRequest;
import cn.ehanmy.hospital.mvp.model.entity.member_info.SendMessageResponse;
import io.reactivex.Observable;


public interface MemberInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();

        void updateMemberInfo(MemberBean memberBean);

        void setEnd(boolean isEnd);

        void showError(boolean hasDate);

        void startLoadMore();

        void endLoadMore();

        Cache getCache();

        void commentOk();

        void updateRecyclerViewHeight();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<MemberInfoByIdResponse> requestMemberinfoById(MemberInfoByIdRequest request);

        Observable<GetMessageListResponse> getMessageList(GetMessageListRequest request);

        Observable<SendMessageResponse> sendMessage(SendMessageRequest request);
    }
}
