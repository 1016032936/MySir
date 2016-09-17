package com.jswn.mysir;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.jswn.Myview.SettingItemView;
import com.jswn.UtilTools.Content;
import com.jswn.UtilTools.SpUtils;

/**
 * Created by 极速蜗牛 on 2016/9/14 0014.
 */
public class Setting_Actvity extends Activity implements View.OnClickListener {
    public SettingItemView ren_voice;
    public SettingItemView voice_yusu;
    public String[] mRenwu = new String[]{"男声", "女生"};
    public int mItem_code;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        initUI();
    }

    public void initUI() {
        ren_voice = (SettingItemView) findViewById(R.id.ren_voice);
        ren_voice.setOnClickListener(this);
        String renru = mRenwu[SpUtils.getInt(this, Content.RENWU_VOICE, 0)];
        ren_voice.setInfo(renru);

        voice_yusu = (SettingItemView) findViewById(R.id.voice_sudu);
        String sudu = String.valueOf(SpUtils.getInt(getApplicationContext(), Content.SUDU, 0));
        voice_yusu.setInfo(sudu);
        voice_yusu.setOnClickListener(this);
    }

    public void initdata() {
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ren_voice:
                showSetVoice_dg();
                break;
            case R.id.voice_sudu:
                show_yusu_dg();
                break;
        }
    }

    /**
     * 人物选择
     */
    public void showSetVoice_dg() {
        mItem_code = SpUtils.getInt(getApplicationContext(), Content.RENWU_VOICE, 0);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.imagewoniu);
        builder.setTitle("人物声音选择");
        builder.setSingleChoiceItems(mRenwu, mItem_code, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpUtils.setInt(getApplicationContext(), Content.RENWU_VOICE, which);
                ren_voice.setInfo(mRenwu[which]);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 语调速度
     */
    public void show_yusu_dg() {
        final SeekBar seekBar = new SeekBar(getApplicationContext());
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.imagewoniu);
        builder.setTitle("语音语速");
        builder.setView(seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean romUser) {
                SpUtils.setInt(getApplicationContext(), Content.SUDU, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                voice_yusu.setInfo(SpUtils.getInt(getApplicationContext(), Content.SUDU, 0) + "");
            }
        });
        builder.show();
    }
}
