package cn.ehanmy.hospital.mvp.ui.adapter;

import android.widget.TextView;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsSpecValueBean;
import cn.ehanmy.hospital.mvp.ui.widget.LabelsView;

/**
 * Created by guomin on 2018/9/8.
 */
public class SpecLabelTextProvider implements LabelsView.LabelTextProvider<GoodsSpecValueBean> {
    @Override
    public CharSequence getLabelText(TextView label, int position, GoodsSpecValueBean data) {
        return data.getSpecValueName();
    }
}
