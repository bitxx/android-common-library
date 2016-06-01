package com.wj.common;


import com.wj.base.BaseApplication;

/**
 * Created by wuj on 2016/6/1.
 * 通用变量
 */
public class Constants {
    //本地路径
    public final static String FILE_ROOT = BaseApplication.getInstance().getExternalCacheDir().getPath()+"/";//所有本地保存文件的根路径
    public final static String TEMP_PATH = FILE_ROOT + ".temp/";
    public final static String PROTRAIT_PATH = FILE_ROOT + ".protrait/";  //用于存放头像
}
