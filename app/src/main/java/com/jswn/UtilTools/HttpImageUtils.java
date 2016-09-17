package com.jswn.UtilTools;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 极速蜗牛 on 2016/9/17 0017.
 */
public class HttpImageUtils {
    public static InputStream getImageStream(String url) throws Exception {
        InputStream in = null;
        URL urlIg = new URL("http://m.image.so.com/i?q=美女#q=%E7%BE%8E%E5%A5%B3&id=c441c62958e6ee60348353bb0136e3e6&multiple=1&itemindex=0&dataindex=6&jsonsrc=3");
        HttpURLConnection connection = (HttpURLConnection) urlIg.openConnection();
        connection.setConnectTimeout(3000);//设置网络连接超时的时间为3秒
        connection.setRequestMethod("GET");        //设置请求方法为GET
        connection.setDoInput(true);                //打开输入流
        int responseCode = connection.getResponseCode();    // 获取服务器响应值
        if (responseCode == 200){
            in = connection.getInputStream();
            return in;
        }
       return null;
    }
}
