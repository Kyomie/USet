package cn.ytang.james.uset.db;

import android.app.Activity;
import android.content.Context;

import cn.ytang.james.uset.db.service.UserService;

/**
 *  将已创建的多表service对象封装在此
 *
 * Created by James on 17/11/20.
 */

public class DaoUtil {

    private static UserService sUserService;
    private static Context sContext;

    public static void init(Context context) {
        if(context instanceof Activity) {
            sContext = context.getApplicationContext();
        } else {
            sContext = context;
        }
    }

    public static UserService getUserService() {
        if(sUserService == null) {
            sUserService = new UserService(sContext);
        }
        return sUserService;
    }

}
