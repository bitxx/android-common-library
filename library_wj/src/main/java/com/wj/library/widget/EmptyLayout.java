/**
 * 自行重写，空内容时候状态
 */
package com.wj.library.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wj.library.R;

/**
 * 该组件很常用,也很容易实现,这是从OSC中抽出来后,进一步加工为自己需要的,供自己需要地时候使用
 */
public class EmptyLayout extends LinearLayout implements OnClickListener {
    private Context context;
    private ImageView img;
    private TextView tv;
    private RelativeLayout rlEmpty;
    private ProgressBar animProgressBar;
    private boolean clickEnable = true;  //是否可点击
    private OnClickListener listener;
    private int errorState;
    private String strNoDataContent = "";

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    public static final int NO_LOGIN = 6;
    public static final int COMMON_ERROR = 7; //通用错误

    public EmptyLayout(Context context) {
        super(context);
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        View view = View.inflate(context, R.layout.weight_empty_layout, null);
        img = (ImageView) view.findViewById(R.id.img_error_layout);
        tv = (TextView) view.findViewById(R.id.tv_error_layout);
        rlEmpty = (RelativeLayout) view.findViewById(R.id.rl_empty);
        animProgressBar = (ProgressBar) view.findViewById(R.id.animProgress);
        setOnClickListener(this);
        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (clickEnable) {
                    if (listener != null)
                        listener.onClick(v);
                }
            }
        });
        addView(view);
    }

    public void dismiss() {
        errorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState() {
        return errorState;
    }

    public boolean isLoadError() {
        return errorState == NETWORK_ERROR;
    }

    public boolean isLoading() {
        return errorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v) {
        if (clickEnable) {

            if (listener != null)
                listener.onClick(v);
        }
    }

    public void setErrorMessage(String msg) {
        tv.setText(msg);
    }

    public void setErrorImage(int imgResource) {
        try {
            img.setImageResource(imgResource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setErrorType(int i) {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                errorState = NETWORK_ERROR;
                img.setBackgroundResource(R.mipmap.pagefailed_bg);
                img.setVisibility(View.VISIBLE);
                animProgressBar.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                errorState = NETWORK_LOADING;
                animProgressBar.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                animProgressBar.setVisibility(View.VISIBLE);
                setTvNoDataContent();
                /********网络相关，暂时不用************/
                clickEnable = false;
                break;
            case NODATA:
                errorState = NODATA;
                img.setBackgroundResource(R.mipmap.page_icon_empty);
                img.setVisibility(View.VISIBLE);
                animProgressBar.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = false;
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;
            case NODATA_ENABLE_CLICK:
                errorState = NODATA_ENABLE_CLICK;
                img.setBackgroundResource(R.mipmap.page_icon_empty);
                img.setVisibility(View.VISIBLE);
                animProgressBar.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case COMMON_ERROR:
                errorState = NETWORK_ERROR;
                img.setBackgroundResource(R.mipmap.pagefailed_bg);
                img.setVisibility(View.VISIBLE);
                animProgressBar.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = false;
                break;
            case NO_LOGIN:
                errorState = NO_LOGIN;
                img.setBackgroundResource(R.mipmap.page_icon_empty);
                img.setVisibility(View.VISIBLE);
                animProgressBar.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = false;
                break;
            default:
                break;
        }
    }

    /**
     * 当没有数据时，提示是因为什么情况而没数据
     *
     * @param noDataContent
     */
    public void setNoDataContent(String noDataContent) {
        this.strNoDataContent = noDataContent;
    }

    /**
     * 该layout点击事件
     *
     * @param listener
     */
    public void setOnLayoutClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void setTvNoDataContent() {
        if (errorState == NO_LOGIN) {
            tv.setText(R.string.empty_no_login);
            return;
        }
        if (errorState == NODATA_ENABLE_CLICK) {
            tv.setText(R.string.empty_no_date_click);
            return;
        }
        if (errorState == NODATA) {
            tv.setText(R.string.empty_no_date_click);
            return;
        }
        if (errorState == NETWORK_ERROR) {
            tv.setText(R.string.empty_click);
            return;
        }
        if (errorState == COMMON_ERROR) {
            tv.setText(R.string.empty_err);
            return;
        }
        if (!strNoDataContent.equals(""))
            tv.setText(strNoDataContent);
        else {
            tv.setText(R.string.empty_no_date);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (visibility == View.GONE)    //当前emptyLayout状态
            errorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}
