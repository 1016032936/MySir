package com.jswn.mysir;

import android.app.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jswn.MyService.VoiceService;
import com.jswn.XunFeiYuyin.VoiceListen;

import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends FragmentActivity {


    private TextView wenzi_bt;
    private TextView yuying_bt;
    private TextView voice_make;
    private ViewPager viewPager_vg;
    private List<Fragment> mListVG;
    private FragmentPagerAdapter mFgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        InitView();
        initData();
        Intent intent = new Intent(Home_Activity.this, VoiceService.class);
        startService(intent);
    }

    private void initData() {
        wenzi_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager_vg.setCurrentItem(0);
            }
        });
        yuying_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, List_app_Activity.class);
                startActivity(intent);
            }
        });
        voice_make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * 初始化
     */
    private void InitView() {
        voice_make = (TextView) findViewById(R.id.voice_make);
        wenzi_bt = (TextView) findViewById(R.id.wenzi);
        yuying_bt = (TextView) findViewById(R.id.yuyin);

        viewPager_vg = (ViewPager) findViewById(R.id.Viewpager_vg);
        Chat_fg_actvity chat = new Chat_fg_actvity();
        /**
         * 测试活动
         */
//        TestActvity test = new TestActvity();

        mListVG = new ArrayList<Fragment>();

        mListVG.add(chat);
//        mListVG.add(test);

        mFgAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mListVG.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mListVG.get(arg0);
            }
        };

        viewPager_vg.setAdapter(mFgAdapter);
    }

}
