package com.jswn.mysir;

import android.app.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.os.Bundle;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends FragmentActivity {

    private ViewPager viewPager_vg;
    private List<Fragment> mListVG;
    private FragmentPagerAdapter mFgAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        InitView();
    }

    /**
     * 初始化
     */
    private void InitView() {
        viewPager_vg = (ViewPager) findViewById(R.id.Viewpager_vg);
        Chat_fg_actvity chat = new Chat_fg_actvity();
        mListVG = new ArrayList<Fragment>();
        mListVG.add(chat);

        mFgAdapter =  new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mListVG.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mListVG.get(arg0);
            }
        };

        viewPager_vg.setAdapter(mFgAdapter);
    }

}
