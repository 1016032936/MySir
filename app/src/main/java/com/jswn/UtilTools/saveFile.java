package com.jswn.UtilTools;

import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 极速蜗牛 on 2016/9/17 0017.
 */
public class saveFile {
    static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/jswn";

    public static void saveFile(String text) {

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long time = System.currentTimeMillis();
        String timestr = String.valueOf(time);
        File file1 = new File(file, timestr + "jswn.txt");
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(file1);
            fout.write(text.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
