package cn.ytang.james.uset.application;

import android.app.Application;
import android.content.Context;

import cn.ytang.james.uset.common.CrashHandler;
import cn.ytang.james.uset.conf.GlobalConfig;
import cn.ytang.james.uset.db.DaoUtil;

/**
 * Created by James on 17/11/9.
 */

public class USetApplication extends Application {

  private static Context sContext;

  @Override
  public void onCreate() {
    super.onCreate();
    sContext = this;
    GlobalConfig.setAppContext(sContext);
    init();
    DaoUtil.init(this);
  }

  private void init() {
    CrashHandler crashHandler = CrashHandler.getInstance();
    crashHandler.init(sContext);
  }

}
