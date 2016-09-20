package com.jswn.UtilTools;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 极速蜗牛 on 2016/9/19 0019.
 */
public class readFile {
    /**
     * 文件的路劲
     */
    static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jswn";

    /**
     * 获取文本的路经的
     *
     * @return
     */
    public static List<File> getFilesPath() {
        List<File> listSB = new ArrayList<File>();
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File[] files = file1.listFiles();
        if (files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                listSB.add(files[i]);
            }
            return listSB;
        }
        return null;
    }

    /**
     * 读取文件内容
     *
     * @param file
     * @return
     */
    public static StringBuffer getlistFilesText(File file) {
        FileInputStream fin = null;
        StringBuffer stb = new StringBuffer();
        InputStreamReader in = null;
        BufferedReader bufferedReader = null;
        try {
            fin = new FileInputStream(file);
            in = new InputStreamReader(fin, "utf-8");
            bufferedReader = new BufferedReader(in);
            int temp;
            while ((temp = bufferedReader.read()) != -1) {
                stb.append((char) temp);
            }
            Log.i("bw", "      " + file.getAbsolutePath() + "   " + temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
                if (in != null){
                    in.close();
                }
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stb;
    }
}
