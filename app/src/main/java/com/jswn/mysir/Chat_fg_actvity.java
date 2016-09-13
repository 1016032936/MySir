package com.jswn.mysir;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jswn.MyAdapter.MyAdapter_list;
import com.jswn.MyBean.Message_tuling;
import com.jswn.UtilTools.HttpUtils;
import com.jswn.XunFeiYuyin.VoiceListen;

import java.util.ArrayList;
import java.util.List;


public class Chat_fg_actvity extends Fragment {
    private Button send_bt;
    private EditText msg_ed;
    public List<Message_tuling> mdata;
    public MyAdapter_list myAdapter_list;
    private ListView mListView;
    private View view;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Message_tuling from_msg = (Message_tuling) msg.obj;
            mdata.add(from_msg);
            myAdapter_list.notifyDataSetChanged();
            mListView.setSelection(mdata.size() - 1);
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_wenzi_activity, null, false);
        InitView();
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void initData() {
        mdata = new ArrayList<Message_tuling>();
        myAdapter_list = new MyAdapter_list(getContext(), mdata);
        mListView.setAdapter(myAdapter_list);
    }

    /**
     * 界面初始化
     */
    public void InitView() {
        mListView = (ListView) view.findViewById(R.id.mlistview);
        send_bt = (Button) view.findViewById(R.id.send_msg_bt);
        msg_ed = (EditText) view.findViewById(R.id.ed_msg);

        send_bt.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                final String msg = msg_ed.getText().toString();

                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(getContext(), "kong", Toast.LENGTH_SHORT).show();
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
