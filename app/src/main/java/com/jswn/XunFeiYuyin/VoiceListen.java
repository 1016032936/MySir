/**
 * SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "=57d6af9a");
 */


package com.jswn.XunFeiYuyin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.jswn.UtilTools.Content;
import com.jswn.UtilTools.SpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by 极速蜗牛 on 2016/9/12 0012.
 */
public class VoiceListen extends Activity {
    private RecognizerDialog mDialog;
    private Mylistener mListener;
    public int code = 0;
    //    SpeechRecognizer speechRecognizer;
    public Context mContext;


    private InitListener mInitlistener = new InitListener() {
        @Override
        public void onInit(int i) {
            if (i != ErrorCode.SUCCESS) {
                Toast.makeText(VoiceListen.this, "error 识别错误", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        UI();
    }

    public void UI() {
        if (mDialog == null) {
            mDialog = new RecognizerDialog(VoiceListen.this, mInitlistener);
        }
        /**
         * 设置识别参数
         //         */
//////        //清空Grammar_ID，防止识别后进行听写时Grammar_ID的干扰
//        mDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, "cloud_grammar");
//        设置听写Dialog的引擎
        mDialog.setParameter(SpeechConstant.DOMAIN, "iat");

        mDialog.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        mDialog.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ASR_PTT, "asr_ptt");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        mDialog.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
        if (mListener == null) {
            mListener = new Mylistener();
        }
        mDialog.setListener(mListener);
        mDialog.show();
    }

    /***
     * 解析识别后的json字符串
     *
     * @param json
     * @return
     */
    public String GetJsonString(String json) {
        if (TextUtils.isEmpty(json)) {
            return "没有识别的结果";
        }

        StringBuilder stb = new StringBuilder();


        try {
            JSONTokener jstoken = new JSONTokener(json);
            JSONObject jsb = new JSONObject(jstoken);

            JSONArray jsa = jsb.getJSONArray("ws");
            for (int i = 0; i < jsa.length(); i++) {
                JSONArray jsItem = jsa.getJSONObject(i).getJSONArray("cw");
                JSONObject jsonObject = jsItem.getJSONObject(0);
                stb.append(jsonObject.getString("w"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stb.toString();
    }

    /**
     * 继承监听方法，重写方法
     */
    class Mylistener implements RecognizerDialogListener {

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            Toast.makeText(VoiceListen.this, GetJsonString(recognizerResult.getResultString()), Toast.LENGTH_SHORT).show();
            if (mDialog != null) {
                mDialog.dismiss();
            }
            Intent intent = new Intent();
            intent.putExtra("msg", GetJsonString(recognizerResult.getResultString()));
            setResult(1, intent);
            Log.i("xunfei", GetJsonString(recognizerResult.getResultString()));
            finish();
        }

        @Override
        public void onError(SpeechError speechError) {
            int errorCoder = speechError.getErrorCode();
            switch (errorCoder) {
                case 10118:
                    Toast.makeText(VoiceListen.this, "你没有讲任何话", Toast.LENGTH_SHORT).show();
                    break;
                case 10204:
                    Toast.makeText(VoiceListen.this, "网络问题", Toast.LENGTH_SHORT).show();
                    break;
                case 10111:
                    Toast.makeText(VoiceListen.this, "引擎初始化出问题，错误码：10111", Toast.LENGTH_SHORT).show();
                    break;
                case 10132:
                    Toast.makeText(VoiceListen.this, "问题，错误码：10132", Toast.LENGTH_SHORT).show();
                case 11201:
                    Toast.makeText(VoiceListen.this, "语音次数限制了", Toast.LENGTH_SHORT).show();
                default:
                    Toast.makeText(VoiceListen.this, "未知错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
