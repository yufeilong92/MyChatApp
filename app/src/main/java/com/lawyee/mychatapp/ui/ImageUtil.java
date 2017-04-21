package com.lawyee.mychatapp.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * All rights Reserved, Designed By www.lawyee.com
 *
 * @version V 1.0 xxxxxxxx
 * @Title: MyChatApp
 * @Package com.lawyee.mychatapp.ui
 * @Description: $todo$
 * @author: YFL
 * @date: 2017/4/21 15:32
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2017 www.lawyee.com Inc. All rights reserved.
 * 注意：本内容仅限于北京法意科技有限公司内部传阅，禁止外泄以及用于其他的商业目
 */


public class ImageUtil {


    /**
     * 计算图片的压缩放置
     *
     * @param options
     * @param reqwith
     * @param reqhight
     * @return
     */
    public static int CalculatelSampleSize(BitmapFactory.Options options, int reqwith, int reqhight) {
        final int height = options.outHeight;
        final int with = options.outWidth;
        int inSampleSize = 1;
        if (height > reqhight || with > reqwith) {
            final int heightRatio = Math.round((float) height / (float) reqhight);
            final int widthRatio = Math.round((float) with / (float) reqwith);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * @param filePath
     * @return 获取压缩后的图片bitmap
     */
    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        //calculae inSampleSize
        options.inSampleSize = CalculatelSampleSize(options, 480, 800);
        //Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    //bitmap 转换成string

    /**
     *
     * @param mContext
     * @param filePath
     * @param fileName
     * @return 获取压缩后图片保存路径
     */
    public static String bitampToString(Context mContext, String filePath, String fileName) {
        Bitmap bitmap = getSmallBitmap(filePath);

        File f = new File(mContext.getCacheDir() + fileName + ".png");
        Toast.makeText(mContext, "" + mContext.getCacheDir() + fileName + ".png", Toast.LENGTH_SHORT).show();
//        File f = new File("/sdcard/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mContext.getCacheDir() + fileName + ".png";

    }

   /* *//**
     * @param bitName
     * @return 获取压缩后图片保存路径
     *//*
    public static final String savePicture(Context mContext, String bitName, Bitmap mBitmap) {
//        Bitmap mBitmap = getSmallBitmap(bitName);
//获取内置存储下的缓存目录，可以用来保存一些缓存文件如图片，当内置存储的空间不足时将系统自动被清除
        File f = new File(Environment.getExternalStorageDirectory() + bitName + ".png");
        Log.d("==", "压缩图片的路径" + Environment.getExternalStorageDirectory() + bitName + ".png");
//        File f = new File("/sdcard/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Environment.getExternalStorageDirectory() + bitName + ".png";
    }
*/
    /**
     * @param filePath
     * @return 获取本地图片的Bitmap
     */
    public static final Bitmap getloadPicBitmap(String filePath) {
        Bitmap bitmap = null;
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }


}
