package com.jswn.MyBean;

import com.jswn.UtilTools.ShowToastUtil;

import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/9 0009.
 */
public class Message_tuling {

    public String name;
    public int code;
    public String msg;
    public String url;
    private Type type;
    private Date date;
    public String desurl;

    public String getDesurl() {
        return desurl;
    }

    public void setDesurl(String desurl) {
        this.desurl = desurl;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public enum Type {
        INCOMING, OUTCOMING
    }

    ;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
