package com.jswn.mysir;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * Created by 极速蜗牛 on 2016/9/18 0018.
 */
public class App_Information_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.app_information_activity);
    }
}
