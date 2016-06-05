package com.wj.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * @version 1.0
 * 在xml中加入此scroll后，其中的view上下滑动有弹性特效
 * 借用了开源中国OSC客户端源码
 *
 * 主要借用了scrollview滚动可以向下增加坐标，向上滚到负坐标
 *
 * @author idea_wj
 */
public class CustomerScrollView extends ScrollView {
	private static final String TAG = CustomerScrollView.class.getSimpleName();

	private static final int size = 4;
	private View inner;
	private float y; //记录滑动前的坐标
	private Rect normal = new Rect();  //用于记录恢复

	public CustomerScrollView(Context context) {
		super(context);
	}

	public CustomerScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 当xml布局执行完毕后，就会调用此方法
	 */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		if (getChildCount() > 0) {
			inner = getChildAt(0); //相当于获取scrollview中，子控件的第一个，控制该子控件的位置
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner == null) {
			return super.onTouchEvent(ev);
		} else {
			commOnTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}

	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			y = ev.getY();
			Log.e(TAG,"action_down,y="+y);
			break;
		case MotionEvent.ACTION_UP:
			Log.e(TAG,"action_up");
			if (isNeedAnimation()) {
				// Log.v("mlguitar", "will up and animation");
				animation();
			}
			break;
		case MotionEvent.ACTION_MOVE: //滑动期间，该事件会一直被触发
			final float preY = y;
			Log.e(TAG,"preY:"+preY);
			float nowY = ev.getY();
			//Log.e(TAG,"action_move,preY="+preY+" nowY:"+nowY);
			/**
			 * size=4 表示 拖动的距离为屏幕的高度的1/4
			 */
			int deltaY = (int) (preY - nowY) / size;  //每两次滑动间隔的差距都除以4，这样最终下滑距离也只有屏幕1/4
			// 滚动
			// scrollBy(0, deltaY);

			y = nowY; //每次下滑都重新更新起始坐标
			if (isNeedMove()) {
				if (normal.isEmpty()) {  //normal初始化与第一个子组件的大小一样
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());
					//Log.e(TAG,"action_move,inner.getLeft()="+inner.getLeft()+" inner.getTop():"+inner.getTop()+" inner.getRight():"+inner.getRight()+" inner.getBottom()):"+inner.getBottom()+" all");
					return;
				}
				int yy = inner.getTop() - deltaY;  //deltaY下拉时候本身是负数，类似匀速增加y坐标，inner.getTop()坐标一直在累加
				//Log.e(TAG,"inner.getTop():"+inner.getTop()+",yy:"+yy+",preY:"+preY+",nowY:"+nowY);

				// 移动布局
				inner.layout(inner.getLeft(), yy, inner.getRight(),
						inner.getBottom() - deltaY);
			}
			break;
		default:
			break;
		}
	}

	public void animation() {
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();
	}

	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	/**
	 * idea_wj 备注:
	 * scrollY=0,scrollview滑动回了顶部
	 * scrollY=inner.getMeasuredHeight() - getHeight(),表示滑块滑到了最底部
	 * 这两种情况下,均需要填充Inner,表示弹性趋于
	 * @return
	 */
	public boolean isNeedMove() {
		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		return scrollY == 0 || scrollY == offset;
	}

}
