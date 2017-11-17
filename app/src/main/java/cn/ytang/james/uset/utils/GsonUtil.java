package cn.ytang.james.uset.utils;

import com.google.gson.Gson;

import cn.ytang.james.uset.base.mvc.BaseModel;

/**
 * 全局使用的 Gson ，避免重复创建。
 *
 * Created by James on 17/11/17.
 */

public class GsonUtil {

    private static Gson sGson = new Gson();

    public static Gson getGson() {
        return sGson;
    }

    public static <T> T fromGson(Class<T> clazz, String jsonString) {
        T model = sGson.fromJson(jsonString, clazz);
        return model;
    }

    public static String toGson(BaseModel clazz) {
        return sGson.toJson(clazz);
    }

}
