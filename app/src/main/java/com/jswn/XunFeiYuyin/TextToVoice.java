package com.jswn.XunFeiYuyin;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by 极速蜗牛 on 2016/9/13 0013.
 */
public class TextToVoice {
    private final Context mContext;
    Mytext2voice mytext2voice;

    public TextToVoice(Context context){
        mContext = context;
    }
    public void Tetx2voice(String str){
        SpeechSynthesizer speechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext,null);
        speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        speechSynthesizer.setParameter(SpeechConstant.SPEED, "50");//设置语速
        speechSynthesizer.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        mytext2voice = new Mytext2voice();
        speechSynthesizer.startSpeaking(str,mytext2voice);

    }

    class Mytext2voice implements SynthesizerListener {

        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    }
}
