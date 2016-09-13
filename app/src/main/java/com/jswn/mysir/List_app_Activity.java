package com.jswn.mysir;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jswn.AppPriviter.AppInfo_priviter;
import com.jswn.MyAdapter.AppListAdapter;
import com.jswn.MyBean.AppInfo;

import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/12 0012.
 */
public class List_app_Activity extends Activity {
    private GridView list_app_gv;
    private AppListAdapter mY_listapp_adapter;
    private List<AppInfo> mApplist;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                list_app_gv.setAdapter(mY_listapp_adapter);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_app);
        initUI();
        initData();
        list_app_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initData() {
        mApplist = AppInfo_priviter.getAppList_Show(this);
        mY_listapp_adapter = new AppListAdapter(this, mApplist);
        new Thread() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }.start();
    }

    /**
     * 初始化Ui
     */
    private void initUI() {
        list_app_gv = (GridView) findViewById(R.id.list_app_gv);
    }


}
