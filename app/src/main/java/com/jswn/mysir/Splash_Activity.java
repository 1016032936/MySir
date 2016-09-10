package com.jswn.mysir;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jswn.UtilTools.ShowToastUtil;
import com.jswn.UtilTools.StreamUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by 极速蜗牛 on 2016/9/7 0007.
 */
public class Splash_Activity extends Activity {
    private RelativeLayout mSplash_act;
    private TextView mVersionName_tv;
    private int mLoactionCode;
    private String mDownLoadUrl;
    private String mVersionDes;

    private final int UPDATE = 0;//版本更新通知码
    private final int URL_ERROR = 1;//url异常
    private final int IO_ERROR = 2;//io异常
    private final int JSON_ERROR = 3;//json文件异常
    private final int HONME_START = 4;//主界面

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showUpdateDialog();
                    break;
                case 1:
                    ShowToastUtil.showToast(getApplicationContext(), "url异常");
                    GoHome();
                    break;
                case 2:
                    ShowToastUtil.showToast(getApplicationContext(), "io异常");
                    GoHome();
                    break;
                case 3:
                    ShowToastUtil.showToast(getApplicationContext(), "json异常");
                    GoHome();
                    break;
                case 4:
                    GoHome();
                    break;
            }
        }
    };

    /**
     * 进入主界面
     */
    public void GoHome() {
        Intent it_home = new Intent(Splash_Activity.this, Home_Activity.class);
        startActivity(it_home);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_actvity);
        initUI();
        initData();
        setAnimation();
    }
    /**
     * 动画
     */
    private void setAnimation() {
        AlphaAnimation ap = new AlphaAnimation(0, 1);
        ap.setDuration(2000);
        mSplash_act.setAnimation(ap);
        mSplash_act.startLayoutAnimation();
    }
    /**
     * 初始化数据
     */
    private void initData() {
        //设置应用版本名称
        mVersionName_tv.setText(getVersionName());
        //获取该应用当前的版本号
        mLoactionCode = getVersionCode();
        CheckVersion(mLoactionCode);
    }

    /**
     * 初始化Ui
     */
    private void initUI() {
        mSplash_act = (RelativeLayout) findViewById(R.id.splash_act);
        mVersionName_tv = (TextView) findViewById(R.id.tv_version_name);
    }

    /**
     * 在splah界面上开启线程进行检测服务器存在的应用的版本
     *
     * @param locationCode 当前版本一个用的versionCode；
     */
    public void CheckVersion(final int locationCode) {
        new Thread(new Runnable() {
            Message msg = Message.obtain();

            @Override
            public void run() {
                URL url = null;
                long startTime = SystemClock.currentThreadTimeMillis();
                try {
                    //访问本地服务器
                    url = new URL("http://192.168.1.103:8088/version.json");
                    HttpURLConnection cn = (HttpURLConnection) url.openConnection();
                    cn.setRequestMethod("GET");
                    cn.setConnectTimeout(2000);
                    cn.setReadTimeout(2000);
                    if (cn.getResponseCode() == 200) {
                        InputStream in = cn.getInputStream();
                        //从流文件中读取到记录版本信息的json文件
                        String text_Json = StreamUtil.streamToString(in);
                        //开始解析json
                        JSONObject jsb = new JSONObject(text_Json);
                        mDownLoadUrl = jsb.getString("downLoadUrl");
                        String versionCode = jsb.getString("versionCode");
                        mVersionDes = jsb.getString("versionDes");
                        String versionName = jsb.getString("versionName");
                        if (locationCode < Integer.parseInt(versionCode)) {
                            //有版本更新
                            msg.what = UPDATE;
                        } else {
                            //直接进入主界面
                            msg.what = HONME_START;
                        }

                    }

                } catch (MalformedURLException e) {
                    msg.what = URL_ERROR;
                } catch (ProtocolException e) {

                } catch (IOException e) {
                    msg.what = IO_ERROR;
                } catch (JSONException e) {
                    msg.what = JSON_ERROR;
                } finally {
                    long endTime = SystemClock.currentThreadTimeMillis();
                    if (endTime - startTime < 3000) {
                        try {
                            Thread.sleep(3000 - (endTime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendEmptyMessage(msg.what);
                }


            }
        }).start();
    }


    /**
     * @return 返回应用的版本名称
     **/
    public String getVersionName() {
        PackageManager packageM = getPackageManager();
        try {
            PackageInfo packageinfo = packageM.getPackageInfo(getPackageName(), 0);
            return packageinfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 返回版本的版本号
     */
    public int getVersionCode() {
        PackageManager packageM1 = getPackageManager();
        try {
            PackageInfo packageinfo1 = packageM1.getPackageInfo(getPackageName(), 0);
            return packageinfo1.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 更新提示窗口
     */
    public void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("版本更新通知");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownApk();
            }
        });
        builder.setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                GoHome();
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 下载新版本
     */
    public void DownApk() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //获取存放路径
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MySir.apk";
            //发送请求
            HttpUtils htuls = new HttpUtils();
            htuls.download(mDownLoadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    File file = responseInfo.result;
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }
            });
        }
    }

    /**
     * 安装apk
     *
     * @param file
     */
    private void installApk(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
    }
}

