package com.wj.library.helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.wj.library.R;
import com.wj.library.common.Constants;
import com.wj.library.util.FileUtil;
import com.wj.library.util.ImageUtils;
import com.wj.library.util.StringUtil;

import java.io.File;

/**
 * @author idea_wj 2016-02-23
 * 用于头像替换的模块
 * @version 1.0
 */
public class ProfileHelper {
    private static final String TAG = ProfileHelper.class.getName();

    private Activity activity;

    private Uri cropUri;
    private Uri origUri;  //头像照片，拍完后尚未处理的路径
    private final static int CROP = 400; //输出图片
    private File portraitFile;
    private String portraitPath;

    public static final int ACTION_TYPE_ALBUM = 0; //相册
    public static final int ACTION_TYPE_PHOTO = 1;  // 相机
    public static final int ACTION_GETIMAGE_BYSDCARD = 2;  //照相和相册结果都是从sd中获取

    public ProfileHelper(Activity activity){
        this.activity = activity;
    }

    public void openProfile(){
        DialogHelper.getSelectDialog(activity, activity.getResources().getStringArray(R.array.choose_picture), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goToSelectPicture(i);
            }
        }).show();
    }

    /**
     * 相册或照相
     * @param position
     */
    private void goToSelectPicture(int position) {
        Intent intent;
        switch (position) {
            case ACTION_TYPE_ALBUM:

                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    activity.startActivityForResult(Intent.createChooser(intent, activity.getResources().getString(R.string.select_profile)),
                            ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
                } else {
                    intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    activity.startActivityForResult(Intent.createChooser(intent, activity.getResources().getString(R.string.select_profile)),
                            ImageUtils.REQUEST_CODE_GETIMAGE_BYSDCARD);
                }
                break;
            case ACTION_TYPE_PHOTO:
                // 判断是否挂载了SD卡
                boolean isCreate = FileUtil.createCachePath(Constants.TEMP_PATH,activity);

                if(isCreate==true) {
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, this.getCamcraTmepFile(Constants.PROTRAIT_PATH));
                    activity.startActivityForResult(intent, ACTION_TYPE_PHOTO);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取拍的照片的路径
     * @return
     */
    private Uri getCamcraTmepFile(String profilePath){
        String storageState = Environment.getExternalStorageState(); //获取sd卡状态
        if(storageState.equals(Environment.MEDIA_MOUNTED)){
            File savedir = new File(profilePath);  //图像路径
            if(!savedir.exists()){
                savedir.mkdirs();
            }
        }else {
            ToastHelper.toastShort(activity, R.string.drawer_can_not_save_protrait);
            return null;
        }
        String timeStamp = System.currentTimeMillis()+"";
        String cropFileName = "protraitTemp_" + timeStamp + ".jpg"; //命名刚拍完照的图像
        String path = Constants.TEMP_PATH + cropFileName; //完整路径
        File srcFile = new File(path);
        cropUri = Uri.fromFile(srcFile);
        this.origUri = this.cropUri;   //原始拍照图像的路径
        return this.cropUri;
    }

    /**
     * Activity 的回调函数 onActivityResult使用
     * 用于判断是相册或者照相回调
     * @param requestCode
     * @param intent
     * @param callbackProfileListener 回调显示
     */
    public void resultSet(int requestCode, Intent intent, ImageView imageView, CallbackProfileListener callbackProfileListener){
        if(requestCode==ACTION_TYPE_PHOTO) { //拍照
            startActionCrop(origUri);
        }if(requestCode== ACTION_TYPE_ALBUM){ //相册获取
            Uri uri = intent.getData();
            startActionCrop(uri);
        }if(requestCode ==  ACTION_GETIMAGE_BYSDCARD){
            callbackProfileListener.callbackProfile(portraitFile.getAbsolutePath());
        }
    }

    /**
     *
     * 用于剪裁拍好的照片或者从相册取出的照片
     */
    private void startActionCrop(Uri uri){
        if(uri==null)
            return;
        Intent intent = new Intent("com.android.camera.action.CROP");

        String path = CommonHelper.getImageAbsolutePath(activity,uri);//4.3以下和4.4以上加载图片方式不同，因为其返回的uri即data格式不同
        uri = Uri.fromFile(new File(path));

        intent.setDataAndType(uri, "image/*");
        intent.putExtra("output", this.getUploadTempFile(uri));//getUploadTempFile，用于获取头像路径，uri主要用来判断当前图像格式
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", CROP);// 输出图片大小
        intent.putExtra("outputY", CROP);
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        activity.startActivityForResult(intent,
                ACTION_GETIMAGE_BYSDCARD);   //加载上传
    }


    /**
     * 根据拍照或者相册中选中的图片，进行剪裁，
     * 该方法用于获取剪裁后的图片完整路径
     * @param uri ：原始待裁剪的图像的路径(图像库返回的)，主要用于判断是什么格式的图像
     */
    private Uri getUploadTempFile(Uri uri) {
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            File savedir = new File(Constants.PROTRAIT_PATH);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        } else {
            ToastHelper.toastShort(activity, R.string.drawer_can_not_save_protrait);
            return null;
        }
        String timeStamp = System.currentTimeMillis() + "";
        String thePath = ImageUtils.getAbsoluteImagePath(activity, uri);
        String ext = FileUtil.getFileFormat(thePath); //用于获取原图像格式
        ext = StringUtil.isEmpty(ext) ? "jpeg" : ext;
        // 照片命名
        String cropFileName = "sja_protrait_" + timeStamp + "." + ext;
        // 裁剪头像的绝对路径
        portraitPath = Constants.PROTRAIT_PATH + cropFileName;
        portraitFile = new File(portraitPath);

        cropUri = Uri.fromFile(portraitFile);
        return this.cropUri;
    }

    public interface CallbackProfileListener{
        void callbackProfile(String filePath);
    }
}





