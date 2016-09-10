package com.jswn.UtilTools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 极速蜗牛 on 2016/9/7 0007.
 */
public class ShowToastUtil {
    public final static void showToast(Context cnt, String text){
        Toast.makeText(cnt,text,Toast.LENGTH_SHORT).show();
    }
}
