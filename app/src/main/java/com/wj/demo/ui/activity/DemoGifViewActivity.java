package com.wj.demo.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ToastHelper;
import com.wj.library.helper.ToolbarHelper;
import com.wj.library.widget.GifView;

/**
 * Gif动画
 * Created by wuj on 2016/6/7.
 * 使用须知：在该demo中，若设置播放次数，播放结束后，不应该再点击开始/暂停按钮，此逻辑在现实中不存在，
 *
 * 功能：
 * 1.为gif动画设置播放次数
 * 2.设置动画是否暂停
 * 3.设置动画恢复初始
 * 4.监听指定次数播放完毕后的操作
 * @version 1.0
 */
public class DemoGifViewActivity extends BaseActivity {
    private static String TAG = DemoGifViewActivity.class.getSimpleName();

    private Toolbar toolbar;
    private TextView tvTitle;
    private GifView gif1;
    private GifView gif2;
    private GifView gif3;
    private Button btChange;  //开始暂停
    private Button btRestart;  //重置

    @Override
    protected void initView() {
        setContentView(R.layout.activity_demo_gif_view);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);
        gif1 = (GifView)findViewById(R.id.gif_1);
        gif2 = (GifView)findViewById(R.id.gif_2);
        gif3 = (GifView)findViewById(R.id.gif_3);
        gif1.setTimes(1);
        gif2.setTimes(2);
        gif3.setTimes(5);
        btChange = (Button)findViewById(R.id.bt_change);
        btRestart = (Button)findViewById(R.id.bt_restart);
        btChange.setOnClickListener(this);
        btRestart.setOnClickListener(this);
        tvTitle.setText("GIF动画");
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);

        gif1.setOnFinishListener(new GifView.FinishListener() {
            @Override
            public void finish() {
                ToastHelper.toastShort(DemoGifViewActivity.this,"第一个gif图片播放1次结束啦！");
            }
        });

        gif2.setOnFinishListener(new GifView.FinishListener() {
            @Override
            public void finish() {
                ToastHelper.toastShort(DemoGifViewActivity.this,"第二个gif图片播放2次结束啦！");
            }
        });

        gif3.setOnFinishListener(new GifView.FinishListener() {
            @Override
            public void finish() {
                ToastHelper.toastShort(DemoGifViewActivity.this,"第三个gif图片播放5次结束啦！");
            }
        });
    }

    @Override
    protected void myOnClick(View view) {
        super.myOnClick(view);
        switch (view.getId()){
            case R.id.bt_change:
                if(gif1.isPaused()){
                    gif1.setPaused(false);
                    gif2.setPaused(false);
                    gif3.setPaused(false);
                    btChange.setText("暂停");
                }else {
                    gif1.setPaused(true);
                    gif2.setPaused(true);
                    gif3.setPaused(true);
                    btChange.setText("开始");
                }
                break;
            case R.id.bt_restart:
                gif1.restart();
                gif2.restart();
                gif3.restart();
                break;
        }
    }
}
