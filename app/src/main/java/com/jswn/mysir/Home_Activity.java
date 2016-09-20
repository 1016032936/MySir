package com.jswn.mysir;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.jswn.MyAdapter.MyAdapter_list;
import com.jswn.MyBean.Message_tuling;
import com.jswn.MyService.VoiceService;
import com.jswn.UtilTools.Content;
import com.jswn.UtilTools.HttpUtils;
import com.jswn.UtilTools.ShowToastUtil;
import com.jswn.UtilTools.SpUtils;
import com.jswn.XunFeiYuyin.TextToVoice;
import com.jswn.XunFeiYuyin.VoiceListen;

import java.util.ArrayList;
import java.util.List;

public class Home_Activity extends Activity implements View.OnClickListener {
    private ImageView yuyin_bt;//语音输入按钮
    private TextView write_voice; //文本录入按钮
    private TextView sousuo_play; //玩转语音
    private TextView show_left_menu;//展示侧滑菜单

    private TextView setting_tv;//侧滑菜单的设置按钮
    private ToggleButton togglebt;//窗口图标开关
    private TextView app_information;//应用信息

    private TextView send_bt; //发送按钮
    private EditText msg_ed; //文字编辑框
    public List<Message_tuling> mdata; //机器人聊天消息类集合
    public MyAdapter_list myAdapter_list; //聊天信息展示适配器
    private ListView mListView; //聊天信息listview
    public TextToVoice mT2V; //文字转语音类

    public WebView show;//web控件
    public TextView bt_tv_web;//web隐藏控件
    private LinearLayout linear;//web隐藏的主体
    /**
     * 用来更新聊天信息的实时变化
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Message_tuling from_msg = (Message_tuling) msg.obj;
            mT2V.Tetx2voice(from_msg.msg);
            /**有url时会显示***/
            startweb(from_msg);

            mdata.add(from_msg);
            //通知适配器更新
            myAdapter_list.notifyDataSetChanged();
            //增加数据源列表数据
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
        //右菜单的控件
        mListView = (ListView) findViewById(R.id.list_message);
        sousuo_play = (TextView) findViewById(R.id.sousuo_play);
        write_voice = (TextView) findViewById(R.id.write_voice);
        yuyin_bt = (ImageView) findViewById(R.id.yuyin);
        send_bt = (TextView) findViewById(R.id.send_wenzi);
        msg_ed = (EditText) findViewById(R.id.ed_msg_1);

        //侧滑菜单道德控件
        setting_tv = (TextView) findViewById(R.id.setting_tv);
        app_information = (TextView) findViewById(R.id.app_information);
        togglebt = (ToggleButton) findViewById(R.id.toggle_onoff);



        togglebt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent voice_service = new Intent(Home_Activity.this, VoiceService.class);
                if (isChecked) {
                    startService(voice_service);
                } else {
                    stopService(voice_service);
                }
            }
        });


        //web控件主体
        show = (WebView) findViewById(R.id.web_intent);
        bt_tv_web = (TextView) findViewById(R.id.bt_tv_web);
        linear = (LinearLayout) findViewById(R.id.Linear);

        //web控件主体的事件
        bt_tv_web.setOnClickListener(this);

        //右菜单的事件响应
        write_voice.setOnClickListener(this);
        sousuo_play.setOnClickListener(this);
        yuyin_bt.setOnClickListener(this);
        send_bt.setOnClickListener(this);

        //侧滑菜单的事件响应
        setting_tv.setOnClickListener(this);
        app_information.setOnClickListener(this);
    }

    /**
     * 语音输入从透明actvity返回的语音文本数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            /**
             * 无论又没url都要在点击发送后隐藏web主体
             */
            linear.setVisibility(View.GONE);

            final String msg = data.getStringExtra("msg");
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
//                    /**有url时会显示***/
//                    startweb(from_msg);

                    Message msg_o = Message.obtain();
                    msg_o.obj = from_msg;
                    mHandler.sendMessage(msg_o);
                }
            }.start();


        }
    }

    /**
     * 该actvity下的各种响应事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /***************右主菜单的事件响应操作*******************/
            //发送文字信息
            case R.id.send_wenzi:
                final String msg = msg_ed.getText().toString();
                /**
                 * 无论又没url都要在点击发送后隐藏web主体
                 */
                linear.setVisibility(View.GONE);

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
                break;
            //语音发送信息
            case R.id.yuyin:
                Intent intent = new Intent(Home_Activity.this, VoiceListen.class);
                startActivityForResult(intent, 1);
                break;
            //语音搜索
            case R.id.sousuo_play:
                Intent play_sousuo = new Intent(Home_Activity.this, Voice_sousuo_Actvity.class);
                startActivity(play_sousuo);
                break;
            //录入文本
            case R.id.write_voice:
                Intent write_intent = new Intent(Home_Activity.this,Memorandum_actvity.class);
                startActivity(write_intent);
                break;

            /***************侧滑菜单的事件响应操作*******************/
            //进入设置操作
            case R.id.setting_tv:
                Intent setting_intent = new Intent(Home_Activity.this, Setting_Actvity.class);
                startActivity(setting_intent);
                break;


            /*****web控件主体****/
            case R.id.bt_tv_web:
                linear.setVisibility(View.GONE);
                break;
            case R.id.app_information:
                Intent intent_app_information = new Intent(Home_Activity.this,App_Information_Activity.class);
                startActivity(intent_app_information);
                break;
        }
    }

    public void startweb(Message_tuling from_msg) {
        String url = from_msg.getUrl();
        String disurl = from_msg.getDesurl();
        if (url != null) {
            linear.setVisibility(View.VISIBLE);
            show.getSettings().setJavaScriptEnabled(true);
            show.getSettings().setBlockNetworkImage(false);
            show.setWebViewClient(new WebViewClient());
            show.loadUrl(url);
            url = "";
        } else if (disurl != null) {
            linear.setVisibility(View.VISIBLE);
            show.getSettings().setJavaScriptEnabled(true);
            show.getSettings().setBlockNetworkImage(false);
            show.setWebViewClient(new WebViewClient());
            show.loadUrl(disurl);
            url = "";
        }
    }
}
