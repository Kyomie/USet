package cn.ytang.james.uset.okhttp;

/**
 * 上层调用接口
 *
 * Created by James on 17/12/1.
 */

public class ApiCreator {

    /**
     * 统一域名 OkHttpConfigImpl中的BP_SIT ＝ "http://api.sit.uset.com"
     */
    public static <T> T createApi(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        return USetOkHttpWrapper.getInstance().getRetrofit().create(clazz);
    }

    /**
     * 此方法是迫不得已用，以后用到该host，一定建议替换成统一域名的。
     * 非统一域名的。可以A接口使http://api.sit.uset.com，B接口使http://baidu.com,此时baseUrl传入的参数使http://baidu.com
     */
    public static <T> T createApiWithSopHost(Class<T> clazz, String baseUrl) {
        if (clazz == null) {
            return null;
        }
        USetOkHttpWrapper.getInstance().updateBaseUrl(baseUrl);
        T t = USetOkHttpWrapper.getInstance().getRetrofit().create(clazz);
        USetOkHttpWrapper.getInstance().updateBaseUrl(baseUrl);
        return t;
    }

}
