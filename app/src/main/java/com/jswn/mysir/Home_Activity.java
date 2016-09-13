package com.jswn.mysir;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.jswn.MyAdapter.MyAdapter_list;
import com.jswn.MyBean.Message_tuling;
import com.jswn.MyService.VoiceService;
import com.jswn.UtilTools.HttpUtils;
import com.jswn.XunFeiYuyin.TextToVoice;
import com.jswn.XunFeiYuyin.VoiceListen;

import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends FragmentActivity {
    private ImageView yuyin_bt;
    private TextView write_voice;
    private Button send_bt;
    private EditText msg_ed;
    public List<Message_tuling> mdata;
    public MyAdapter_list myAdapter_list;
    private ListView mListView;
    public TextToVoice mT2V;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Message_tuling from_msg = (Message_tuling) msg.obj;
            mT2V.Tetx2voice(from_msg.msg);
            mdata.add(from_msg);
            myAdapter_list.notifyDataSetChanged();
            mListView.setSelection(mdata.size() - 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=57d6af9a");
        mT2V = new TextToVoice(this);
        InitView();
        initData();

        Intent intent = new  Intent(Home_Activity.this, VoiceService.class);
        startService(intent);
    }
    /**
     * 初始化数据
     */
    private void initData() {
        mdata = new ArrayList<Message_tuling>();
        myAdapter_list = new MyAdapter_list(Home_Activity.this, mdata);
        mListView.setAdapter(myAdapter_list);
    }

    /**
     * 初始化
     */
    private void InitView() {
        mListView = (ListView) findViewById(R.id.list_message);
        write_voice = (TextView) findViewById(R.id.write_voice);
        yuyin_bt = (ImageView) findViewById(R.id.yuyin);
        send_bt = (Button) findViewById(R.id.send_wenzi);
        msg_ed = (EditText) findViewById(R.id.ed_msg_1);

        write_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        yuyin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoiceListen vcl = new VoiceListen(Home_Activity.this);
                vcl.UI();
            }
        });

        send_bt.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                final String msg = msg_ed.getText().toString();

                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(Home_Activity.this, "kong", Toast.LENGTH_SHORT).show();
                    return;
                }
                //整合数据
                Message_tuling msg_tu = new Message_tuling();
                msg_tu.setMsg(msg);
                msg_tu.setDate(new java.util.Date());
                msg_tu.setType(Message_tuling.Type.OUTCOMING);
                mdata.add(msg_tu);
                //通知适配器去更新
                myAdapter_list.notifyDataSetChanged();
                mListView.setSelection(mdata.size() - 1);
                msg_ed.setText("");
                //获取子线程
                new Thread() {
                    @Override
                    public void run() {
                        //获取到返回的信息

                        Message_tuling from_msg = HttpUtils.sendMessage(msg);
                        Message msg_o = Message.obtain();
                        msg_o.obj = from_msg;
                        mHandler.sendMessage(msg_o);
                    }
                }.start();
            }
        });
    }
}
