package com.jswn.mysir;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;

import com.jswn.AppPriviter.AppInfo_priviter;
import com.jswn.AppPriviter.AppInfo_priviter2;
import com.jswn.MyBean.AppInfo;
import com.jswn.UtilTools.ShowToastUtil;
import com.jswn.XunFeiYuyin.VoiceListen;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 极速蜗牛 on 2016/9/12 0012.
 */
public class Voice_touming extends Activity {
    private List<AppInfo> mApplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //TODO
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.toumin);
        Intent intent = new Intent(getBaseContext(), VoiceListen.class);
//        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final String msg = data.getStringExtra("msg");
        String appstr = ".*应用.*";
        String music = ".*音乐.*";
        String photo = ".*相片.*";
        String phone = "[0-9]*";
        String sms = ".*短信.*";


        if (msg.matches(appstr)) {
            //应用列表
            startapplist();
            finish();
        } else if (msg.matches(phone)) {
            //打电话
            startPhone(msg);
            finish();
        } else if (msg.matches(sms)) {
            //短信
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(msg);
            String number = "123";
            if (m.find()) {
                number = m.group();
                startSms(number);
            }
            finish();

        } else if (msg.matches(music)) {
            //音乐
            startmusic();
            finish();
        } else {
            finish();
        }
    }

    /**
     * 语音识别后启动applist
     */
    public void startapplist() {
        Intent intent = new Intent(Voice_touming.this, List_app_Activity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 打电话
     *
     * @param msg
     */
    public void startPhone(String msg) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + msg));
        startActivity(intent);
    }

    /**
     * 短信
     *
     * @param msg
     */
    public void startSms(String msg) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        ShowToastUtil.showToast(getApplicationContext(), msg);
        intent.setData(Uri.parse("smsto:" + msg));
        startActivity(intent);
    }

    public void startmusic() {
        mApplist = AppInfo_priviter2.getAppList_Show(this);
        int i = 0;
        for (i = 0; i < mApplist.size(); i++) {
            if (mApplist.get(i).name.matches(".*音乐.*")) {
                LaunchApp(mApplist.get(i));
                break;
            }
        }
    }

    public void LaunchApp(AppInfo appInfo) {
        //通过桌面去启动
        PackageManager pm = getPackageManager();
        //通过包名去启动
        Intent launchApp_intent = pm.getLaunchIntentForPackage(appInfo.PackageName);
        if (launchApp_intent != null) {
            startActivity(launchApp_intent);
        } else {
            ShowToastUtil.showToast(this, "系统应用");
        }
    }

}
