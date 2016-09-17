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

    public static void setInt(Context context, String key, int value){
        if (sp == null) {
            sp = context.getSharedPreferences("Voice", Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key, int value){
        if (sp == null) {
            sp = context.getSharedPreferences("Voice", Context.MODE_PRIVATE);
        }
        return sp.getInt(key, value);
    }


    public static void setBoolean(Context context, String key, boolean value){
        if (sp == null) {
            sp = context.getSharedPreferences("Voice", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultvalue){
        if (sp == null) {
            sp = context.getSharedPreferences("Voice", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key,defaultvalue);
    }
}
