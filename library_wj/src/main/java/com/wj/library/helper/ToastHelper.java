package com.wj.library.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * toast管理
 * Created by wuj on 2016/6/1.
 * @version 1.0
 */
public class ToastHelper {

    /**
     * 短，资源文件
     * @param context
     * @param msg
     */
    public static void toastShort(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短，自定义字符串
     * @param context
     * @param msg
     */
    public static void toastShort(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长，资源文件
     * @param context
     * @param msg
     */
    public static void toastLong(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 长，自定义字符串
     * @param context
     * @param msg
     */
    public static void toastLong(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
