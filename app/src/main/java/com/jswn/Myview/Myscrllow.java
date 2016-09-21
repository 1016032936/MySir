package com.jswn.Myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.jswn.mysir.R;

/**
 * Created by 极速蜗牛 on 2016/9/10 0010.
 */
public class Myscrllow extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private boolean isOpen = false;

    //菜单栏的宽度
    private int mMenuWidth;

    private int mScreenWidth;

    private int paddingright = 300;

    private boolean once;

    public Myscrllow(Context context) {


        this(context, null);
        Log.i("myscrow",0+"****************************");
    }

    public Myscrllow(Context context, AttributeSet attrs) {
         super(context,attrs);
//        this(context, null, 0);
        Log.i("myscrow",1+"****************************");
//        super(context, attrs, defStyleAttr);
        //获取我们自定义的属性
//        Log.i("myscrow",2+"****************************");

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Myscrllow, 0, 0);
        int num = ta.getIndexCount();
        for (int i = 0; i < num; i++) {
            int arrt = ta.getIndex(i);
            switch (arrt) {
                case R.styleable.Myscrllow_rightPadding:
                    paddingright = ta.getDimensionPixelSize(arrt,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50, context
                                            .getResources().getDisplayMetrics()));

                    break;
            }
        }

        ta.recycle();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }

    public Myscrllow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取我们自定义的属性
        Log.i("myscrow",2+"****************************");

        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Myscrllow, defStyleAttr, 0);
        int num = ta.getIndexCount();
        for (int i = 0; i < num; i++) {
            int arrt = ta.getIndex(i);
            switch (arrt) {
                case R.styleable.Myscrllow_rightPadding:
                    paddingright = ta.getDimensionPixelSize(arrt,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 50, context
                                            .getResources().getDisplayMetrics()));

                    break;
            }
        }

        ta.recycle();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;
    }





    /**
     * 设置子View的宽和高 设置自己的宽和高
     */

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth
                    - paddingright;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量，将menu隐藏
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                // 隐藏在左边的宽度
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 3) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
//                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = true;
                }
                return true;
        }
        return false;
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen) {
            return;
        }
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }

    public void toogleMenu() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
