package com.wj.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.wj.R;
import com.wj.helper.ToastHelper;
import com.wj.helper.UIHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @version 1.0
 * 文件处理工具
 * @author idea_wj 2015-08-04
 */
public class FileUtil {
    private final static String TAG = "FileUtil";


    /**
     * 保存缓存文件路径
     * @param path
     */
    public static boolean createCachePath(String path, Context context){
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = path;
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (StringUtil.isEmpty(savePath)) {
            ToastHelper.toastShort(context, R.string.common_storage_err);
            return false;
        }
        return true;
    }


    /**
     * Delete file
     *
     * @param filePath
     *            filePath =
     *            android.os.Environment.getExternalStorageDirectory().getPath()
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file == null || !file.exists() || file.isDirectory()) {
            if(file == null)
                Log.e(TAG,"为空");
            if(!file.exists())
                Log.e(TAG,"不存在");
            if(file.isDirectory())
                Log.e(TAG,"路径");
            return false;
        }
        return file.delete();
    }

    /**
     * 获取当前根文件夹下面的所有的图片路径，但是在应用中不适合这样操作，有些重复，先在此保留
     * 之所以说重复，是因为，可以根据listFiles将路径直接保存在Image集合中，不需要多此一举，先生成集合再返回去一个个加
     * @param path
     * @return
     */
    public static ArrayList<String> getFilePathList(File path){
        ArrayList<String> filePathList=new ArrayList<>();
        for(int i=0;i<path.listFiles().length;i++){
            String filePath = path.listFiles()[i].getAbsolutePath();
            String prefix=filePath.substring(filePath.lastIndexOf(".")+1);//获取文件名的后缀
            if(prefix.equals("jpg")||prefix.equals("png")||prefix.equals("jpeg")||prefix.equals("gif")){
                Log.e("FileUtil","文件后缀："+prefix+" 路径："+filePath);
                //添加
                filePathList.add("file://"+filePath);
            }
        }

        return filePathList;
    }

    /**
     * 根据文件绝对路径获取文件名
     * @param filePath
     */
    public static String getFileName(String filePath) {
        if (StringUtil.isEmpty(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);//分离出文件名
    }


    /**
     * 获取文件扩展名
     * @param fileName
     * @return
     */
    public static String getFileFormat(String fileName) {
        if (StringUtil.isEmpty(fileName))
            return "";

        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * sd卡是否存在
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    /**
     * 如果文件路径不存在，则创建
     * @return
     */
    public static File fileNotExistAndMkdir(String filePath){
        File file = null;
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        return file;
    }

    /**
     * 文件是否存在
     * @param filePath
     * @return
     */
    public static boolean fileIsExist(String filePath){
        if(StringUtil.isEmpty(filePath))
            return false;
        File file = null;
        file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param fromFile 被复制的文件
     * @param toFile 复制的目录文件
     * @param rewrite 是否重新创建文件
     *
     * <p>文件的复制操作方法
     */
    public static void copyfile(File fromFile, File toFile,Boolean rewrite ){

        if(!fromFile.exists()){
            return;
        }

        if(!fromFile.isFile()){
            return;
        }
        if(!fromFile.canRead()){
            return;
        }
        if(!toFile.getParentFile().exists()){
            toFile.getParentFile().mkdirs();
        }
        if(toFile.exists() && rewrite){
            toFile.delete();
        }


        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while((c=fosfrom.read(bt)) > 0){
                fosto.write(bt,0,c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
