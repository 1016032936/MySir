package com.jswn.mysir;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jswn.UtilTools.ShowToastUtil;
import com.jswn.XunFeiYuyin.VoiceListen;

/**
 * Created by 极速蜗牛 on 2016/9/13 0013.
 */
public class Voice_sousuo_Actvity extends Activity implements View.OnClickListener{
    private TextView voice_sousuo;
    private TextView start_sousuo;
    private EditText sousuo_ed;
    String path ="https://m.baidu.com/s?from=1012852y&word=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.voice_sousuo);
        initUI();
    }

    public void initUI() {
        voice_sousuo = (TextView) findViewById(R.id.y_sousuo_shuru);
        start_sousuo = (TextView) findViewById(R.id.sousuo_start);
        sousuo_ed = (EditText) findViewById(R.id.sousuo_ed);

        start_sousuo.setOnClickListener(this);
        voice_sousuo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.y_sousuo_shuru:
                Intent intent1 = new Intent(Voice_sousuo_Actvity.this, VoiceListen.class);
                startActivityForResult(intent1, 1);
                break;
            case  R.id.sousuo_start:
                ShowToastUtil.showToast(this,"532");
                Intent intent= new Intent(Intent.ACTION_WEB_SEARCH);
                intent.setAction("android.intent.action.VIEW");
                String text = sousuo_ed.getText().toString();
                path = path+""+text;
                Uri content_url = Uri.parse(path);
                intent.setData(content_url);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            String msg = data.getStringExtra("msg");
            if (TextUtils.isEmpty(msg)){
                ShowToastUtil.showToast(this,"语音输入失败");
            }
            sousuo_ed.setText(msg);
        }
    }
}
