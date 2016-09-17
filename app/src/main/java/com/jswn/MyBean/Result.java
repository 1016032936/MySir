package com.jswn.MyBean;

import java.net.URL;

/**
 * Created by 极速蜗牛 on 2016/9/9 0009.
 */
public class Result {
    public int code;
    public String text;
    public String url;
    public String detailurl;

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
