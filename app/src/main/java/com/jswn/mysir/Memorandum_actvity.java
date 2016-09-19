package com.jswn.mysir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jswn.MyAdapter.write_adapter;
import com.jswn.UtilTools.saveFile;
import com.jswn.XunFeiYuyin.VoiceListen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/17 0017.
 */
public class Memorandum_actvity extends Activity {

    private TextView write_voice;
    private List<String> list_text;
    private write_adapter strAdapter;
    private ListView listView;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String text = msg.obj.toString();
            list_text.add(text);
            strAdapter.notifyDataSetChanged();
            listView.setSelection(list_text.size()-1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.memorandum_actvity);
        initUi();
        initData();
    }

    /****
     * 初始化数据
     */
    private void initData() {
        list_text = new ArrayList<String>();
        list_text.add("默认文本");
        strAdapter = new write_adapter(Memorandum_actvity.this,list_text);
        listView.setAdapter(strAdapter);
    }
    /**
     * 初始化控件
     */
    public void initUi() {
        write_voice = (TextView) findViewById(R.id.writer_voice_bw);
        listView = (ListView) findViewById(R.id.list_bw);

        final Intent intent = new Intent(Memorandum_actvity.this, VoiceListen.class);
        write_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent, 4);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String text = data.getStringExtra("msg");
        new Thread(){
            @Override
            public void run() {
                super.run();
                saveFile.saveFile(text, 1);
            }
        }.start();

        String str = text.substring(0, 5)+"....";
        Message msg = Message.obtain();
        msg.obj = str;
        mHandler.sendMessage(msg);
    }
}
