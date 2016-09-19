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
    public static void saveFile(String text, int postion) {
        File file = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File file1 = new File(file, postion + "jswn.txt");
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
