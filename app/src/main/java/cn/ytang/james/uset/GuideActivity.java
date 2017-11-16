package cn.ytang.james.uset;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import cn.ytang.james.uset.base.BaseActivity;
import cn.ytang.james.uset.mvp.ui.activity.MainActivity;

public class GuideActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!this.isTaskRoot()) {
      // 判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
      // 如果你就放在launcher Activity中话，这里可以直接return了
      Intent mainIntent = getIntent();
      String action = mainIntent.getAction();
      if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
        finish();
        return;// finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
      }
    }

    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.activity_guide);
    jump2Main();
  }

  private void jump2Main() {
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        Intent intent = new Intent();
        intent.setClass(GuideActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }
    };
    timer.schedule(timerTask, 2000L);
  }


}
