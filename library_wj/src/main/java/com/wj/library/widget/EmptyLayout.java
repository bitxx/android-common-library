package com.wj.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wj.library.R;

import java.util.HashMap;
import java.util.Map;

/**
 * 很常用的一个组件，当界面展示为空，需要一个有好的界面展示，此时，由EmptyLayout来统一管理，是非常不错的选择
 *
 * 需要注意，这个layout是一个通用的，仅仅能满足一般需求，剩下的需要自己根据需要完善了。毕竟不同项目，这个变化比较大
 * Created by wuj on 2016/6/26.
 */
public class EmptyLayout extends LinearLayout implements View.OnClickListener
{

    public static final int ERROR = 1;  //各种原因造成的显示失败
    public static final int LOADING = 2; //加载中
    public static final int GONE = 3; //除去EmptyLaout
    public static final int NODATA = 4; //没有数据

    private static final Map msg = new HashMap(){
        {
            put(ERROR,"加载失败，请重试");
            put(LOADING,"加载中");
            put(GONE,"隐藏");
            put(NODATA,"没有内容");
        }
    };

    private boolean isClick = false;  //该emptyLayout是否允许点击事件
    private int nowType = LOADING;   //默认类型
    private View.OnClickListener listener;

    private ImageView img;
    private TextView tv;
    private ProgressBar animProgressBar;

    public EmptyLayout(Context context) {
        super(context);
        initView(context);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    @Override
    public void onClick(View v) {
        if(listener!=null&&isClick==true)
            listener.onClick(v);
    }

    /**
     * 初始化
     * @param context
     */
    private void initView(Context context){
        View view = View.inflate(context, R.layout.weight_empty_layout,null);
        img = (ImageView) view.findViewById(R.id.img_error_layout);
        tv = (TextView) view.findViewById(R.id.tv_error_layout);
        animProgressBar = (ProgressBar) view.findViewById(R.id.animProgress);
        view.setOnClickListener(this);  //Emptylayout的
        setType(nowType,false);
        addView(view);  //切记要将布局文件加入
    }

    /**
     * 获取点击事件
     * @param listener
     */
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    /**
     * 设置文字提示
     * @param msg
     */
    public void setMsg(String msg){
        tv.setText(msg);
    }

    /**
     * 设置图片资源文件
     * 当res中传入0时，图片被删除
     * @param res
     */
    public void setImg(int res){
        img.setVisibility(View.VISIBLE);
        img.setBackgroundResource(res);
    }

    /**
     * 设置图片是否显示
     * @param v
     */
    public void setImgVisibility(boolean v){
        if(v)
            img.setVisibility(View.VISIBLE);
        else
            img.setVisibility(View.GONE);
    }

    /**
     * 设置view显示类型
     * @param type
     * @param isClick
     */
    public void setType(int type,boolean isClick){
        nowType = type;
        this.isClick = isClick;
        animProgressBar.setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
        tv.setText(msg.get(nowType).toString());
        switch(nowType){
            case ERROR:
                //根据需要自行完善
                break;
            case LOADING:
                setImgVisibility(false);
                animProgressBar.setVisibility(View.VISIBLE);
                break;
            case GONE:
                setVisibility(View.GONE);
                break;
            case NODATA:
                //根据需要自行完善
                break;
        }
    }

}