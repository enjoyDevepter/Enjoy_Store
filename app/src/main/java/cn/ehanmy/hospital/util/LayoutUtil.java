package cn.ehanmy.hospital.util;

import android.util.TypedValue;

import com.jess.arms.utils.ArmsUtils;

public class LayoutUtil {
    /**
     * 根据手机分辨率从DP转成PX
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        float scale = ArmsUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = ArmsUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        float scale = ArmsUtils.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @return
     */

    public static int px2sp(float pxValue) {
        final float fontScale = ArmsUtils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
