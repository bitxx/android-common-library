package com.wj.library.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import com.wj.library.R;

/**
 * 可以清空的EditText
 * 该组件是网上很流行的一种通用的实现方式，很简单，该组件是很久前从SCDN上照的，代码没有怎么改变，后期若有需要，可根据具体情况修改
 * 之所以继承AutoCompleteTextView，是因为，很多情况下，输入的内容都要考虑到历史记录
 *
 * 主要是利用了TextView右侧的drawable来实现的，因此，可以自定义导入一个清空的图标到右侧
 *
 * Created by wuj on 2016/6/26.
 */
public class ClearEditText extends AutoCompleteTextView implements TextWatcher,View.OnFocusChangeListener{

    private Drawable drawable;  //清空按钮的图片资源，因为是继承自AutoCompleteTextView，需要获取其右侧的图片资源

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        drawable = getCompoundDrawables()[2];  //获取右侧的图片资源
        if(drawable == null)
            drawable = getResources().getDrawable(R.mipmap.et_clear);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        setDrawVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                    if(drawable!=null){
                        //点击范围在清空图标附近时候，才可以响应清空
                        boolean isClear = event.getX() > (getWidth() - getTotalPaddingRight())&& (event.getX() < ((getWidth() - getPaddingRight())));
                        if(isClear)
                            setText("");
                    }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void setDrawVisible(boolean visible) {
        Drawable right = visible ? drawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],getCompoundDrawables()[1], right, getCompoundDrawables()[3]);  //保证原先已有图标不变
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
            setDrawVisible(text.length() > 0);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus)
            setDrawVisible(getText().length() > 0);
        else
            setDrawVisible(false);
    }
}
