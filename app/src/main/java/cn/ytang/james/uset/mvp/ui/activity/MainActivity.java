package cn.ytang.james.uset.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.List;

import cn.ytang.james.uset.R;
import cn.ytang.james.uset.base.BaseActivity;
import cn.ytang.james.uset.db.DaoUtil;
import cn.ytang.james.uset.db.entity.User;
import cn.ytang.james.uset.mvp.ui.fragment.MeijuFragment;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar mBottomNavigationBar;
    private MeijuFragment mMeijuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
        initview();
        initBottomNavigationBar();
        new Thread(new Runnable() {
            @Override
            public void run() {
                calculateLoginTimes();
            }
        }).start();
    }

    private void initview(){
        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar_container);
    }

    private void initBottomNavigationBar() {
        mBottomNavigationBar.setAutoHideEnabled(true);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        mBottomNavigationBar.setBarBackgroundColor(R.color.white);
        mBottomNavigationBar.setInActiveColor(R.color.bottom_nav_normal);
        mBottomNavigationBar.setActiveColor(R.color.bottom_nav_selected);

        BottomNavigationItem meijuItem = new BottomNavigationItem(R.drawable.home_bottom_bar_ic1,"灵感");
        BottomNavigationItem allArticleItem = new BottomNavigationItem(R.drawable.home_bottom_bar_ic2,"经典");
        BottomNavigationItem jujiItem = new BottomNavigationItem(R.drawable.home_bottom_bar_ic3,"句集");
        BottomNavigationItem originalItem = new BottomNavigationItem(R.drawable.home_bottom_bar_ic4,"原创");

        mBottomNavigationBar.addItem(meijuItem).addItem(allArticleItem).addItem(jujiItem).addItem(originalItem);
        mBottomNavigationBar.setFirstSelectedPosition(0);
        mBottomNavigationBar.initialise();
        mBottomNavigationBar.setTabSelectedListener(this);

        setDefaultFragment();

    }

    private void setDefaultFragment() {
        if (mMeijuFragment == null) {
            mMeijuFragment = new MeijuFragment();
        }

        addFragment(mMeijuFragment);
        getSupportFragmentManager().beginTransaction().show(mMeijuFragment).commit();

    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment != null && !fragment.isAdded()) {
            ft.add(R.id.ll_content, fragment);
        }
        ft.commit();
    }

    private void hideAllFragment(){

    }

    private void hideFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (fragment != null && fragment.isAdded()) {
            ft.hide(fragment);
        }
        ft.commit();
    }

    //记录登录次数
    private void calculateLoginTimes(){
        List<User> users = DaoUtil.getUserService().getUserByName("ytang");
        if (users == null || users.size() == 0) {
            User user = new User(null, "ytang", "1437", 1,"version update test");
            DaoUtil.getUserService().insertObject(user);
        } else {
            User user = users.get(0);
            user.setLoginTimes(user.getLoginTimes() + 1);
            user.setDesc("version update test1");
            DaoUtil.getUserService().updateObject(user);
        }

    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0:
                if (mMeijuFragment == null) {
                    mMeijuFragment = new MeijuFragment();
                }
                addFragment(mMeijuFragment);
                getSupportFragmentManager().beginTransaction().show(mMeijuFragment).commit();
                break;
            case 1:
                if (mMeijuFragment == null) {
                    mMeijuFragment = new MeijuFragment();
                }
                addFragment(mMeijuFragment);
                break;
            case 2:
                if (mMeijuFragment == null) {
                    mMeijuFragment = new MeijuFragment();
                }
                addFragment(mMeijuFragment);
                break;
            case 3:
                if (mMeijuFragment == null) {
                    mMeijuFragment = new MeijuFragment();
                }
                addFragment(mMeijuFragment);
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
