package com.jswn.mysir;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jswn.MyAdapter.write_adapter;
import com.jswn.UtilTools.Content;
import com.jswn.UtilTools.ShowToastUtil;
import com.jswn.UtilTools.SpUtils;
import com.jswn.UtilTools.readFile;
import com.jswn.UtilTools.saveFile;
import com.jswn.XunFeiYuyin.VoiceListen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/17 0017.
 */
public class Memorandum_actvity extends Activity implements View.OnClickListener {

    private TextView write_voice;
    private TextView ed_tv_bw;
    private TextView delete_bw;

    private List<String> list_text;
    private write_adapter strAdapter;
    private ListView listView;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String text = msg.obj.toString();
            list_text.add(text);
            strAdapter.notifyDataSetChanged();
            listView.setSelection(list_text.size() - 1);
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
        if (readFile.getFilesPath() != null) {
            List<File> list_file = readFile.getFilesPath();
            for (int i = 0; i < list_file.size(); i++) {
                StringBuffer sb = readFile.getlistFilesText(list_file.get(i));
                String str = sb.toString();
                if (str.length()>5){
                    str = str.substring(0, str.length() / 2) + "....";
                }
                list_text.add(str);
            }
        }
        strAdapter = new write_adapter(Memorandum_actvity.this, list_text);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PopupWindow(view);
                //记录条目索引值，用来在dialog编辑查找文件显示云文件内容
                SpUtils.setInt(getApplicationContext(), Content.BEIWNGLU, position);
            }
        });
    }

    /**
     * 备忘录的弹窗操作
     *
     * @param view
     */
    public void PopupWindow(View view) {
        View popuview = View.inflate(getApplicationContext(), R.layout.popupitem, null);

        ed_tv_bw = (TextView) popuview.findViewById(R.id.ed_tv_bw);
        delete_bw = (TextView) popuview.findViewById(R.id.delete_tv_bw);

        ed_tv_bw.setOnClickListener(this);
        delete_bw.setOnClickListener(this);

        PopupWindow popupWindow = new PopupWindow(popuview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置透明的颜色
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //指定偏移位置
        popupWindow.showAsDropDown(view, view.getWidth() - popuview.getWidth() + 50, -view.getHeight() + 30);
    }


    /**
     * 语音返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String text = data.getStringExtra("msg");
        new Thread() {
            @Override
            public void run() {
                super.run();
                saveFile.saveFile(text);
            }
        }.start();
        String str = text.substring(0, text.length() / 2) + "....";
        Message msg = Message.obtain();
        msg.obj = str;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_tv_bw:
                View view = View.inflate(getApplicationContext(), R.layout.write_dialog, null);
                final EditText ed_show = (EditText) view.findViewById(R.id.ed_show);

                //读取该listView上对应的文本文件显示在dialog上用来修改编辑
                int postion = SpUtils.getInt(getApplicationContext(), Content.BEIWNGLU, 0);
                List<File> file = new ArrayList<File>();
                file = readFile.getFilesPath();
                ed_show.setText(readFile.getlistFilesText(file.get(postion)).toString());

                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(view);
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = ed_show.getText().toString();
                        saveFile.saveFile(text);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.delete_tv_bw:
                int postion_delete = SpUtils.getInt(getApplicationContext(), Content.BEIWNGLU, 0);
                list_text.remove(postion_delete);
                strAdapter.notifyDataSetChanged();
                listView.setSelection(list_text.size() - 1);
                List<File> file1 = new ArrayList<File>();
                file1 = readFile.getFilesPath();
                file1.get(postion_delete).delete();
                break;
        }
    }
}
