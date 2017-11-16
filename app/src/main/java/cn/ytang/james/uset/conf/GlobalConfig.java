package cn.ytang.james.uset.conf;

import android.content.Context;

/**
 *
 *
 * Created by James on 17/11/16.
 */

public class GlobalConfig {

    private static Context appContext;

    public GlobalConfig() {
    }

    public static void setAppContext(Context context) {
        appContext = context;
    }

    public static Context getAppContext() {
        return appContext;
    }

}
