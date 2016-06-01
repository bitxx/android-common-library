package com.wj.helper;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.wj.R;


/**
 * @author idea_wj 2016-01-14
 * 对话框辅助类
 * @version 1.0
 */
public class DialogHelper {
    private static ProgressDialog progressDialog;

    /***
     * 获取一个dialog
     * @param context
     * @return
     */
    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    /***
     * 获取一个耗时等待对话框
     * @param context
     * @param message
     * @return
     */
    public static ProgressDialog getWaitDialog(Context context, String message) {
        ProgressDialog waitDialog = new ProgressDialog(context);
        if (!TextUtils.isEmpty(message)) {
            waitDialog.setMessage(message);
        }
        return waitDialog;
    }

    /***
     * 获取一个信息对话框，注意需要自己手动调用show方法显示
     * @param context
     * @param message
     * @param onClickListener
     * @return
     */
    public static AlertDialog.Builder getMessageDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getResources().getString(R.string.common_confirm), onClickListener);
        return builder;
    }

    public static AlertDialog.Builder getMessageDialog(Context context, String message) {
        return getMessageDialog(context, message, null);
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, int msg, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getResources().getString(R.string.common_confirm), onClickListener);
        builder.setNegativeButton(context.getResources().getString(R.string.common_cancel), null);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String msg, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getResources().getString(R.string.common_confirm), onClickListener);
        builder.setNegativeButton(context.getResources().getString(R.string.common_cancel), null);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, int msg, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getResources().getString(R.string.common_confirm), onOkClickListener);
        builder.setNegativeButton(context.getResources().getString(R.string.common_cancel), onCancleClickListener);
        return builder;
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String msg, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancleClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton(context.getResources().getString(R.string.common_confirm), onOkClickListener);
        builder.setNegativeButton(context.getResources().getString(R.string.common_cancel), onCancleClickListener);
        return builder;
    }


    public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setItems(arrays, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setCancelable(false);
        builder.setPositiveButton(context.getResources().getString(R.string.common_cancel), null);
        return builder;
    }

    public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onClickListener) {
        return getSelectDialog(context, "", arrays, onClickListener);
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setCancelable(true);
        builder.setNegativeButton(context.getResources().getString(R.string.common_cancel), null);
        return builder;
    }

    public static AlertDialog.Builder getSingleChoiceDialog(Context context, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
        return getSingleChoiceDialog(context, "", arrays, selectIndex, onClickListener);
    }
}
