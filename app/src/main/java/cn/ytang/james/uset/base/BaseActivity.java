package cn.ytang.james.uset.base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.ytang.james.uset.R;
import cn.ytang.james.uset.common.ActivityManager;

public class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityManager.getInstance().pushActivity(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ActivityManager.getInstance().popActivity(this);
  }
}
