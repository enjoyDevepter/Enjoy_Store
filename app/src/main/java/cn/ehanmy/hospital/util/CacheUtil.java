package cn.ehanmy.hospital.util;

import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.IntelligentCache;
import com.jess.arms.utils.ArmsUtils;

public class CacheUtil {

    // 保存用户信息
    public static final String CACHE_KEY_USER = "cache_key_user";
    public static final String CACHE_KEY_USER_HOSPITAL_INFO = "CACHE_KEY_USER_HOSPITAL_INFO";
    public static final String CACHE_KEY_USER_LOGIN_NAME = "cache_key_user_login_name";

    // 保存当前的用户code
    public static final String CACHE_KEY_MEMBER = "cache_key_member";

    private static Cache getExtras() {
        return ArmsUtils.obtainAppComponentFromContext(ArmsUtils.getContext()).extras();
    }

    public static void saveCache(String key, Object value) {
        getExtras().put(key, value);
    }

    /**
     * 自动以常量的方式存储，适合生命周期等同于应用的属性
     */
    public static void saveConstant(String key, Object value) {
        getExtras().put(IntelligentCache.KEY_KEEP + key, value);
    }

    public static void clearConstant(String key) {
        getExtras().remove(IntelligentCache.KEY_KEEP + key);
    }

    public static <T> T getCaChe(String key) {
        return (T) getExtras().get(key);
    }

    public static <T> T getConstant(String key) {
        return (T) getExtras().get(IntelligentCache.KEY_KEEP + key);
    }

    /**
     * 自动获取属性，优先获取全局常量，如果没有获取到才获取局部常量
     */
    public static Object getExtras(String key) {
        Object constant = getConstant(key);
        if (null == constant) {
            return getCaChe(key);
        }
        return constant;
    }
}
