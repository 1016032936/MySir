package com.jswn.MyBean;

import java.util.Date;

/**
 * Created by 极速蜗牛 on 2016/9/9 0009.
 */
public class Message_tuling {

    public String name;
    public String msg;
    private Type type;
    private Date date;

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
