package com.jswn.AppPriviter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jswn.MyBean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/12 0012.
 */
public class AppInfo_priviter {
    private static List<AppInfo> mList_app;

    public static List<AppInfo> getAppList_Show(Context context) {
        mList_app = new ArrayList<AppInfo>();

        PackageManager pm = context.getPackageManager();

        for (PackageInfo packInfo : pm.getInstalledPackages(0)) {
            AppInfo appinfo = new AppInfo();
            /* 获取应用的包名 */
            appinfo.PackageName = packInfo.packageName;
            //获取应用的名称
            ApplicationInfo applicationInfo = packInfo.applicationInfo;
            appinfo.name = applicationInfo.loadLabel(pm).toString();

            //获取应用的图标
            appinfo.icon = applicationInfo.loadIcon(pm);

            //判断是否为系统应用
            if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                //系统用户
                appinfo.isSystem = true;
//                continue;
            } else {
                appinfo.isSystem = false;
                //判断是否手机应用
                if ((applicationInfo.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE) {
                    //系统用户
                    appinfo.isSdCard = true;
                } else {
                    appinfo.isSdCard = false;
                }
                mList_app.add(appinfo);
            }


        }
        return mList_app;
    }
}
