package com.wj.library.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;



import java.lang.reflect.Field;
import java.util.List;

/**
 * @author idea_wj 2015-08-04
 * @version 1.0
 *          对手机设备的一些处理
 *          <p>
 *          注意！！！！
 *          关于网络是否连通的判断，还有一种特殊情况，比如校园网，以下方法虽然返回网络成功连接，但由于没有登录校园账户，仍旧不可以访问网络
 *          这种情况暂时没有好的处理方法，需要在返回值中进行特殊处理
 */

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class DeviceUtil {

    /**
     * 拨出电话号
     * @param phoneNum
     * @param context
     */
    public static void callPhone(String phoneNum,Context context){
        Intent phoneIntent = new Intent(
                "android.intent.action.CALL", Uri.parse("tel:"
                + phoneNum));
        context.startActivity(phoneIntent);
    }

    /**
     * 获取IMEI
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tel = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tel.getDeviceId();
    }

    /**
     * 获取DeviceID
     *
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 判断GPS是否打开
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = ((LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return accessibleProviders != null && accessibleProviders.size() > 0;
    }

    /**
     * 检查某权限是否可用
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context context,String permission){
        PackageManager pkm = context.getPackageManager();
        return (PackageManager.PERMISSION_GRANTED == pkm.checkPermission(permission, context.getPackageName()));
    }

    /**
     * 请求权限
     * @param permission 所要请求的权限们
     * @param requestPermissionCode 请求码
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void requestPermission(FragmentActivity activity, String[] permission, int requestPermissionCode){
        activity.requestPermissions(permission,
                requestPermissionCode);
    }

}