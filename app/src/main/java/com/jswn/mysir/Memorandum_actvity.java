package com.jswn.mysir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.jswn.UtilTools.saveFile;
import com.jswn.XunFeiYuyin.VoiceListen;

/**
 * Created by 极速蜗牛 on 2016/9/17 0017.
 */
public class Memorandum_actvity extends Activity {

    private TextView write_voice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.memorandum_actvity);
        initUi();
    }
    public void initUi(){
        write_voice = (TextView) findViewById(R.id.writer_voice_bw);
        final Intent intent = new Intent(Memorandum_actvity.this, VoiceListen.class);
        write_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,4);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String text = data.getStringExtra("msg");
        saveFile.saveFile(text,1);
    }
}
