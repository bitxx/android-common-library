package com.wj.library.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by idea_wj on 16/6/29.
 * 软键盘相关辅助类
 */
public class KeyBoardUtils {
    /**
     * 打卡软键盘
     *
     * @param view
     * @param mContext 上下文
     */
    public static void openKeybord(View view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param view
     * @param mContext 上下文
     */
    public static void closeKeybord(View view, Context mContext) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE);

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
