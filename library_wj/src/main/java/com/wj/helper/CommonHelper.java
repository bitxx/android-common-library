package com.wj.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;


import com.wj.R;
import com.wj.util.FileUtil;
import com.wj.util.SDCardUtils;
import com.wj.util.StringUtil;

import java.io.File;

/**
 * @author idea_wj 2015-08-22
 * @version 1.0
 *          该应用中公用的一些方法，因为没法归类，放在此处。和工具类有所区别
 */
public class CommonHelper {
    private static final String TAG = CommonHelper.class.getName();


    /**
     * 原照片路径，返回新的路径，用于之后图像生成
     *
     * @param fromPath 原图片路径
     * @param toPath   新图片地址
     * @return
     */
    public static String getPath(String fromPath, String toPath, Context context,String userId) {
        String newPath;
        if (SDCardUtils.checkSDCardAvailable()) {
            File savedir = new File(toPath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            ToastHelper.toastShort(context, R.string.common_storage_err);
            return null;
        }
        String timeStamp = System.currentTimeMillis() + "";
        String ext = "";
        if (!StringUtil.isEmpty(fromPath))
            ext = FileUtil.getFileFormat(fromPath); //用于获取原图像格式
        ext = StringUtil.isEmpty(ext) ? "jpg" : ext;

        // 照片命名
        String cropFileName = "";
        if (StringUtil.isEmpty(userId)==false)
            cropFileName = "sja_" + userId + "_" + timeStamp + "." + ext;  //命名规则：bbpp_+当前用户userind+时间戳
        else
            cropFileName = "sja_" + "_" + timeStamp + "." + ext;  //当user为空（正常情况不会，以防万一），默认路径
        newPath = toPath + cropFileName;
        return newPath;
    }

    /**
     * 从配置文件中读取配置信息。meta是键值对，需要用name获取value
     * 要注意，若配置信息中，value只含有数字时候，会被认为是Int型
     * @param name
     */
    public static Object getMetaInfo(String name,Context context) {
        try {
            ApplicationInfo appInfo = context.getApplicationContext().getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Object value = appInfo.metaData.get(name);
            return value;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * base64编码手机号 密码
     * @return
     */
    public static String getAuthorization(String username,String pwd){
        //String authString = username + ":" + CyptoUtil.getMDpwd);  //测试用户,加md5
        String authString = username + ":" + pwd;  //密码不加md5
        Log.e(TAG,"authString:"+authString+" username:"+username+" pwd:"+pwd );
        byte[] authEncBytes = Base64.encode(authString.getBytes(), 0); //Base64
        String authStr =  new String(authEncBytes);
        //Log.e("User","authStr:"+authStr);
        return  authStr;
    }

    /************************************根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换**********************************************************************/
    /**
     *
     * @param context
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}