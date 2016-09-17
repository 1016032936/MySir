package com.jswn.Myview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.jswn.mysir.R;

/**
 * Created by 极速蜗牛 on 2016/9/14 0014.
 */
public class SettingItemView extends RelativeLayout {


    private TextView setting_item_title;
    private TextView setting_text;
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    //自定义的控件属性
    private static final String SETTING_TITLE = "setting_title";


    private String mSetting_title;


    public SettingItemView(Context context) {
        super(context);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        ViewInit(context);

    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }


    /**
     * 自定义属性的方法
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        mSetting_title = attrs.getAttributeValue(NAMESPACE,SETTING_TITLE);
    }

    /**
     * 自定控件的布局初始化
     *
     * @param context
     */
    public void ViewInit(Context context) {
        View view = View.inflate(context, R.layout.settingitem,this);
        setting_item_title  = (TextView) view.findViewById(R.id.setting_item_title);
        setting_text= (TextView) view.findViewById(R.id.setting_text);
        setting_item_title.setText(mSetting_title);
    }



    public void setInfo(String ren){
        setting_text.setText(ren);
    }
}
