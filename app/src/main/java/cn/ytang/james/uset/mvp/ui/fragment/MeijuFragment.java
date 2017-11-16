package cn.ytang.james.uset.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.ytang.james.uset.R;
import cn.ytang.james.uset.base.BaseFragment;
import cn.ytang.james.uset.mvp.ui.adapter.TitleTabAdapter;

/**
 * 第一个tab（灵感）
 *
 * Created by James on 17/11/16.
 */

public class MeijuFragment extends BaseFragment {

    private static final String TAG = MeijuFragment.class.getSimpleName();

    private static final String TYPE1 = null;
    private static final String TYPE2 = "shouxiemeiju";
    private static final String TYPE3 = "jingdianduibai";

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, ">>> onCreateView()");

        View view = inflater.inflate(R.layout.fragment_meiju, container, false);

        initviews(view);
        initFragments();

        return view;
    }

    private void initviews(View v) {
        mTabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) v.findViewById(R.id.viewPager);
    }

    private void initFragments() {
        MeijuListFragment fragment1 = MeijuListFragment.newInstance(TYPE1);
        MeijuListFragment fragment2 = MeijuListFragment.newInstance(TYPE2);
        MeijuListFragment fragment3 = MeijuListFragment.newInstance(TYPE3);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        List<String> titles = new ArrayList<>();
        titles.add("美图美句");
        titles.add("手写句子");
        titles.add("经典对白");

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setTag(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setTag(titles.get(2)));

        TitleTabAdapter adapter = new TitleTabAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
