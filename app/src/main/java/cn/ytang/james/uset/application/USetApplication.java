package cn.ytang.james.uset.application;

import android.app.Application;
import android.content.Context;

import cn.ytang.james.uset.common.CrashHandler;
import cn.ytang.james.uset.conf.GlobalConfig;

/**
 * Created by James on 17/11/9.
 */

public class USetApplication extends Application {

  private static Context sContext;

  public static Context getGlobalConetxt() {
    return sContext;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    sContext = this;
    GlobalConfig.setAppContext(sContext);
    init();
  }

  private void init() {
    CrashHandler crashHandler = CrashHandler.getInstance();
    crashHandler.init(sContext);
  }
}
