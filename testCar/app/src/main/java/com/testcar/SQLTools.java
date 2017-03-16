package com.testcar;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2016/8/24.
 */
public class SQLTools {
    // public static final String COUNTR_ACTION = "com.sql.country";
    // public static final String PROVINCE_ACTION = "com.sql.province";
    // public static final String CITY_ACTION = "com.sql.city";
    // public static final String REGIN_ACTION = "com.sql.region";


    // 从asserts目录下拷贝文件到files
    public static SQLiteDatabase opendatabase(Context context) {
        // 获取输出流,文件存储目录:data/data/包名/files目录下，文件名相同
        File file = new File("/sdcard/fanxin/car.db");
        // 当文件不存在的时候：才去拷贝，已经存在的不再去拷贝了。
        if (!file.exists()) {
            AssetManager assetManager = context.getAssets();
            try {
                // 获取输入流
                InputStream is = assetManager.open("car.db");
                FileOutputStream fos = new FileOutputStream(file);
                // 开始读和写
                byte[] bys = new byte[1024];
                int len;
                while ((len = is.read(bys)) != -1) {
                    fos.write(bys, 0, len);
                }
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return SQLiteDatabase.openOrCreateDatabase(file, null);
    }
}
