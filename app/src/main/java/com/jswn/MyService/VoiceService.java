package com.jswn.MyService;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.jswn.XunFeiYuyin.VoiceListen;
import com.jswn.mysir.R;
import com.jswn.mysir.Voice_touming;

/**
 * Created by 极速蜗牛 on 2016/9/7 0007.
 */
public class VoiceService extends Service {
    private View mVIew;
    private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private WindowManager mWm;
    private int mScreenWidth;
    private int mScreenHeight;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        //获取屏幕宽度
        mScreenWidth = mWm.getDefaultDisplay().getWidth();
        mScreenHeight = mWm.getDefaultDisplay().getHeight();
        showToat();
    }

    public void showToat() {
        //给吐司定义出来的参数(宽高,类型,触摸方式)
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE	//因为吐司需要根据手势去移动,所以必须要能触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.RGBA_8888;
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//不在和吐司类型相互绑定,通话的类型相互绑定
        //将吐司放置在左上角显示
        params.gravity = Gravity.TOP + Gravity.LEFT;
        //定义吐司布局xml--->view挂载到屏幕上
        //土司的显示效果布局文件
        mVIew = View.inflate(this, R.layout.snake_voice, null);
        ImageView aa = (ImageView) mVIew.findViewById(R.id.rocket_image);
//        AnimationDrawable ad = (AnimationDrawable) aa.getBackground();
//        ad.start();
        mVIew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VoiceListen voiceListen = new VoiceListen(getBaseContext());
                voiceListen.UI();
//                Intent intent = new Intent(getBaseContext(), Voice_touming.class);
//                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
            }
        });
        mVIew.setOnTouchListener(new View.OnTouchListener() {
            private int startx;
            private int starty;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        Intent intent = new Intent(getBaseContext(), Voice_touming.class);
//                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
                        //获取按下的坐标
                        startx = (int) event.getRawX();
                        starty = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //获取移动的坐标
                        int movex = (int) event.getRawX();
                        int movey = (int) event.getRawY();

                        int disx = movex - startx;
                        int disy = movey - starty;

                        params.x = movex;
                        params.y = movey;

                        mWm.updateViewLayout(mVIew, params);
                        startx = (int) event.getRawX();
                        starty = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }
                return true;
            }
        });
        mWm.addView(mVIew, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mVIew != null && mWm != null) {
            mWm.removeView(mVIew);
        }
    }
}
