package com.jswn.MyBean;

import android.graphics.drawable.Drawable;

/**
 * Created by 极速蜗牛 on 2016/9/12 0012.
 */
public class AppInfo {
    public String name;//应用的名称
    public String PackageName;//应用的包名
    public boolean isSdCard;//是否为sd卡应用
    public boolean isSystem;//是否系统应用
    public Drawable icon;//应用图标
    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSdCard() {
        return isSdCard;
    }

    public void setSdCard(boolean sdCard) {
        isSdCard = sdCard;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

}
