package com.wj.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author idea_wj 2015-08-04
 * @version 1.0
 *          图片处理工具，建议优先选择xutils中提供的工具处理
 */
public class ImageUtils {
    private static String TAG = "ImageUtils";

    private static int COMPRESS_QUALITY = 50;
    private static int COMPRESS_SIZE_STANDART = 1280;

    /** 请求相册 */
    public static final int REQUEST_CODE_GETIMAGE_BYSDCARD = 0;
    /** 请求相机 */
    public static final int REQUEST_CODE_GETIMAGE_BYCAMERA = 1;


    /**
     * 获取图片路径 2014年8月12日
     *
     * @param uri
     * @return E-mail:mr.huangwenwei@gmail.com
     */
    public static String getImagePath(Uri uri, Activity context) {

        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            String ImagePath = cursor.getString(columIndex);
            cursor.close();
            return ImagePath;
        }

        return uri.toString();
    }




    /**
     * 通过系统的uri获取文件的绝对路径
     *
     * @param uri
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String getAbsoluteImagePath(Activity activity, Uri uri) {
        String imagePath = "";
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, proj, // Which columns to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                imagePath = cursor.getString(column_index);
            }
        }
        return imagePath;
    }


    /**
     * 2014年8月13日
     *
     * @param uri
     * @param context
     *            E-mail:mr.huangwenwei@gmail.com
     */
    public static Bitmap loadPicasaImageFromGalley(final Uri uri,
                                                   final Activity context) {
        final Bitmap[] bitmap = new Bitmap[1];
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME };
        Cursor cursor = context.getContentResolver().query(uri, projection,
                null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();

            int columIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME);
            if (columIndex != -1) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            bitmap[0] = MediaStore.Images.Media
                                    .getBitmap(context.getContentResolver(),
                                            uri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
            cursor.close();
            return bitmap[0];
        } else
            return null;
    }

    /**
     * 获取图片缩略图 只有Android2.1以上版本支持
     *
     * @param imgName
     * @param kind
     *            MediaStore.Images.Thumbnails.MICRO_KIND
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Bitmap loadImgThumbnail(Activity context, String imgName,
                                          int kind) {
        Bitmap bitmap = null;

        String[] proj = { MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME };

        Cursor cursor = context.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj,
                MediaStore.Images.Media.DISPLAY_NAME + "='" + imgName + "'",
                null, null);

        if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {
            ContentResolver crThumb = context.getContentResolver();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;
            bitmap = MethodsCompat.getThumbnail(crThumb, cursor.getInt(0),
                    kind, options);
        }
        return bitmap;
    }

    public static Bitmap loadImgThumbnail(String filePath, int w, int h) {
        Bitmap bitmap = getBitmapByPath(filePath);
        return zoomBitmap(bitmap, w, h);
    }


    /**
     * 获取bitmap
     *
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath) {
        return getBitmapByPath(filePath, null);
    }

    public static Bitmap getBitmapByPath(String filePath,
                                         BitmapFactory.Options opts) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            File file = new File(filePath);
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return bitmap;
    }



    /**
     * 根据所给图片路径，该路径下的图像
     *
     * @param context
     * @param path
     * @return
     */
    public static Bitmap getBitmap(Context context, String path) {
        FileInputStream fis = null;
        Bitmap bitmap = null;
        try {
            fis = context.openFileInput(path);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return bitmap;
    }


    /**
     * 根据当前本地保存的图片，自定义设计缩略图，scaleSize自行决定尺寸
     *
     * @param path     图片资源的存放路径
     * @param scalSize 缩小的倍数
     * @return
     */
    public static Bitmap loadResBitmap(String path, int scalSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = scalSize;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        return bmp;
    }


    public static int[] getImageSize(String path){
        BitmapFactory.Options opts = new BitmapFactory.Options();

        opts.inJustDecodeBounds = true;
        // 原始图片bitmap
        try {
            getBitmapByPath(path, opts);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new int[]{opts.outWidth, opts.outHeight};
    }

    /**
     * 创建并返回缩略图
     * 缩略图生成并保存在本地
     *
     * @param context
     * @param largeImagePath 原始大图路径
     * @param thumbfilePath  输出缩略图路径
     * @param square_size    输出图片宽度
     * @param quality        输出图片质量
     * @throws IOException
     */
    public static void createImageThumbnail(Context context,
                                            String largeImagePath, String thumbfilePath,boolean flag, int square_size,
                                            int quality) throws Exception {
        if(flag){
            BitmapFactory.Options opts = new BitmapFactory.Options();

            opts.inJustDecodeBounds = true;
            // 原始图片bitmap
            getBitmapByPath(largeImagePath, opts);

            // 原始图片的高宽
            int[] cur_img_size = new int[]{opts.outWidth,
                    opts.outHeight};
            // 计算原始图片缩放后的宽高
            int[] new_img_size = scaleImageSize(cur_img_size, square_size);
            opts.inJustDecodeBounds = false;
            //Log.e(TAG,"HV:"+new_img_size[0]+" "+new_img_size[1]);
/*        opts.outWidth = new_img_size[0];
        opts.outHeight = new_img_size[1];*/
            //opts.inSampleSize = 2;
            Bitmap bitmap = getBitmapByPath(largeImagePath, opts);
            Bitmap cur_bitmap = zoomBitmap(bitmap, new_img_size[0], new_img_size[1]);
            if (cur_bitmap == null)
                return;

            //for samsung camera, rotate the img
            int angle = readPictureDegree(largeImagePath);
            if (angle != 0) {
                cur_bitmap = rotaingImageView(angle, cur_bitmap);
            }
            // 生成缩放后的bitmap
            //Bitmap thb_bitmap = zoomBitmap(cur_bitmap, new_img_size[0],new_img_size[1]);
            // 生成缩放后的图片文件
            saveImageToSD(context, thumbfilePath, cur_bitmap, quality);
            bitmap.recycle();
            cur_bitmap.recycle();
        }else {
            Bitmap bitmap = getBitmapByPath(largeImagePath,null);
            saveImageToSD(context, thumbfilePath, bitmap, 100);
            bitmap.recycle();
        }

    }

    /**
     * 旋转图片
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        //回收原图片内存
        bitmap.recycle();
        return resizedBitmap;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 对拍摄的图片进行压缩处理,保持图片不失色，主要用来上传图片到服务器
     * @param flag
     */
    public static void bitmapCompress(String inPath, String outPath, Context context,boolean flag) throws Exception {
        // 压缩上传的图片
        createImageThumbnail(context, inPath, outPath,flag, COMPRESS_SIZE_STANDART, COMPRESS_QUALITY);

    }

    /**
     * 写图片文件到SD卡
     *
     * @throws IOException
     */
    public static void saveImageToSD(Context ctx, String filePath,
                                     Bitmap bitmap, int quality) throws Exception {
        if (bitmap != null) {
            File file = new File(filePath.substring(0,
                    filePath.lastIndexOf(File.separator)));

            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
            bos.close();

            scanPhoto(ctx, file.getPath());
            //ImageUtils.scanPhoto(ctx,filePath);
        }
    }

    /**
     * 让Gallery上能马上看到该图片,发送广播到系统的媒体库去更新
     */
    public static void scanPhoto(Context ctx, String imgFileName) {
        if(imgFileName.contains("sja")) {   //只更新含sja文件夹及其子文件的图片内容
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(imgFileName);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
            ctx.sendBroadcast(mediaScanIntent);
        }
    }

    /**
     * 计算缩放图片的宽高
     *
     * @param img_size
     * @param square_size
     * @return
     */
    public static int[] scaleImageSize(int[] img_size, int square_size) {
        if (img_size[0] <= square_size && img_size[1] <= square_size)
            return img_size;
        double ratio = square_size
                / (double) Math.max(img_size[0], img_size[1]);
        return new int[]{(int) (img_size[0] * ratio),
                (int) (img_size[1] * ratio)};
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        Bitmap newbmp = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidht = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidht, scaleHeight);
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                    true);
        }
        return newbmp;
    }

    /**
     * 截取一个组件高度
     *
     * **/
    public static void getCutImg(String tempPath,View view) {
        int h = view.getWidth();
        Bitmap bitmap = null;

        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(h, view.getHeight(),
                Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
        bitmap.recycle();
    }

/*






    public static void getCutImg(final String tempPath,  ScrollView scrollview){

final View view = scrollview.getChildAt(0);

                //view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//                view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
                view.buildDrawingCache();
                Bitmap bitmap = view.getDrawingCache();

                // 测试输出
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(tempPath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    if (null != out) {
                        bitmap.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, out);
                        out.flush();
                        out.close();
                    }
                } catch (IOException e) {
                    // TODO: handle exception
                }
                bitmap.recycle();






    }*/

    /**
     * 截取整个滚动的屏幕
     *
     * **/
    public static boolean getCutImg(String tempPath,ScrollView scrollView) {
        /*int itemHeight = 0;
        int maxHeight = 0;*/
        /*if(DeviceUtil.screenWidth>1080)
            maxHeight = DeviceUtil.screenWidth*8000/480;  //三星等手机屏幕宽度在1440左右的，截取的比较短
        else
            maxHeight = DeviceUtil.screenWidth*12000/480;//根据宽度为480的酷派手机计算出来的，在480时候，长度为12000可以正常显示，等比放大到任何手机上，大多数手机没问题，可以显示25张左右图片*/
        int h = 0;
        Bitmap bitmap = null;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++)
            h += scrollView.getChildAt(i).getHeight();
        /*if(h>maxHeight)
            h = maxHeight;
        // 创建对应大小的bitmap*/
        try {
            bitmap = Bitmap.createBitmap(scrollView.getChildAt(0).getWidth(), h,
                    Bitmap.Config.RGB_565);
        }catch (Exception e){
            return false;
        }
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                //bitmap = zoomBitmap(bitmap,AlbumDetailActivity.IMAGE_SHOW_SIZE, AlbumDetailActivity.IMAGE_SHOW_SIZE*h/DeviceUtil.screenWidth);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
        bitmap.recycle();
            return true;
    }

    /**
     * dip转换为px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转换为dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}