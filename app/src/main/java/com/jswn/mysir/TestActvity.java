package com.jswn.mysir;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jswn.XunFeiYuyin.VoiceListen;

/**
 * Created by 极速蜗牛 on 2016/9/8 0008.
 */
public class TestActvity extends Activity {
    Button send_msg_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_wenzi_activity);
        send_msg_bt = (Button) findViewById(R.id.send_msg_bt);
        send_msg_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoiceListen voiceListen = new VoiceListen(TestActvity.this);
                voiceListen.UI();
            }
        });
    }
}
