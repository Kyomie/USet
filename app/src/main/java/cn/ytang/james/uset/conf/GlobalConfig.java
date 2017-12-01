package cn.ytang.james.uset.conf;

import android.content.Context;

import cn.ytang.james.uset.db.gen.DaoSession;

/**
 *
 *
 * Created by James on 17/11/16.
 */
public class GlobalConfig {

    public static final boolean DEBUG = true;

    private static String BaseUrl = "http://api.uset.sit.com";

    private static Context appContext;
    private static DaoSession daoSession;

    public GlobalConfig() {
    }

    public static void setAppContext(Context context) {
        appContext = context;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static void setDaoSession(DaoSession daoSession) {
        GlobalConfig.daoSession = daoSession;
    }

    public static String getBaseUrl() {
        return BaseUrl;
    }

    public static void setBaseUrl(String baseUrl) {
        BaseUrl = baseUrl;
    }
}
