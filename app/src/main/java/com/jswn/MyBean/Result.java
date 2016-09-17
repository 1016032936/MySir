package com.jswn.MyBean;

import java.net.URL;
import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/9 0009.
 */
public class Result {
    public int code;
    public String text;
    public String url;
    public List<list> list;

    public List<com.jswn.MyBean.list> getList() {
        return list;
    }

    public void setList(List<com.jswn.MyBean.list> list) {
        if (list != null){
            this.list = list;
        }
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
