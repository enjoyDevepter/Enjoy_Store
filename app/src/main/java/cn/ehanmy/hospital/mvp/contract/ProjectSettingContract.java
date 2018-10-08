package cn.ehanmy.hospital.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.Category;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryRequest;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.CategoryResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.ProjectSettingResponse;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectRequest;
import cn.ehanmy.hospital.mvp.model.entity.user.SettingProjectResponse;
import io.reactivex.Observable;


public interface ProjectSettingContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void updateCategory(List<Category> categoryList,List<String> selectList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<CategoryResponse> getCategory(CategoryRequest request);
        Observable<ProjectSettingResponse> getProjectSetting(ProjectSettingRequest request);
        Observable<SettingProjectResponse> setProjectSetting(SettingProjectRequest request);
    }
}
