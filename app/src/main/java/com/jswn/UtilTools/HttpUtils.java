package com.jswn.UtilTools;


import com.google.gson.Gson;
import com.jswn.MyBean.Message_tuling;
import com.jswn.MyBean.Result;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Created by 极速蜗牛 on 2016/9/9 0009.
 */
public class HttpUtils {
    final static String url = "http://www.tuling123.com/openapi/api";
    static String API_KEY = "a849e13fddee4b119c51fcc6638335ca";

    public final static String doGet(String msg) {
        String result = "";
        String url_cn = setParams(msg);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStream is = null;

        try {
            URL ul = new URL(url_cn);
            HttpURLConnection cn = (HttpURLConnection) ul.openConnection();
            cn.setRequestMethod("GET");
            cn.setConnectTimeout(3 * 2000);
            cn.setReadTimeout(3 * 2000);
            is = cn.getInputStream();
            int temp = 0;
            byte[] buf = new byte[124];
            while ((temp = is.read(buf)) != -1) {
                bos.write(buf, 0, temp);
            }
            bos.flush();
            result = new String(bos.toByteArray());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 将要发送的信息整合成json
     *
     * @param msg
     * @return
     */
    public static Message_tuling sendMessage(String msg) {
        Message_tuling message_tuling = new Message_tuling();
        String json_text = doGet(msg);
        Gson gson = new Gson();
        Result result = null;
        try {
            result = gson.fromJson(json_text, Result.class);
            message_tuling.setMsg(result.getText());
        } catch (Exception e) {
            message_tuling.setMsg("系统繁忙");
        }
        message_tuling.setDetailurl(result.getDetailurl());
        message_tuling.setCode(result.getCode());
        message_tuling.setUrl(result.getUrl());
        message_tuling.setDate(new Date());
        message_tuling.setType(Message_tuling.Type.INCOMING);
        return message_tuling;

    }

    /**
     * 将信息整合到api链接上
     *
     * @param msg
     * @return
     */
    public static String setParams(String msg) {
        String url1 = "";
        try {
            url1 = url + "?key=" + API_KEY + "&info="
                    + URLEncoder.encode(msg, "UTF-8");
        } catch (Exception e) {

        }
        return url1;
    }
}
