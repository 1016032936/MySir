package com.jswn.UtilTools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 极速蜗牛 on 2016/9/13 0013.
 */
public class SpUtils {

    public static SharedPreferences sp = null;

    public static void setString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences("Voice", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }


    public static String getString(Context context, String key, String value) {

        if (sp == null) {
            sp = context.getSharedPreferences("Voice", Context.MODE_PRIVATE);
        }
        return sp.getString(key, value);
    }
}
