package com.wj.demo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.domain.Item;
import com.wj.library.listener.MyOnClickListener;

import java.util.ArrayList;

/**
 * 创建列表项
 * Created by wuj on 2016/6/1.
 * @version 1.0
 */
public class ViewHolderItem extends RecyclerView.ViewHolder {

    private static final String TAG = ViewHolderItem.class.getName();
    private Context context;
    private View view;
    private RelativeLayout rlItem;
    private TextView tvName;

    public ViewHolderItem(View itemView,Context context) {
        super(itemView);
        this.view = itemView;
        this.context = context;
        rlItem = (RelativeLayout)view.findViewById(R.id.rl_item);
        tvName = (TextView)view.findViewById(R.id.tv_name);
    }

    /**
     * 将数据绑定到item
     */
    public void bindItem(ArrayList<Item> items,int position){
        Item item = items.get(position);
        tvName.setText(item.getName());
        rlItem.setOnClickListener(new MyOnClickListener(){

            @Override
            public void myOnClick(View view) {

            }
        });
    }
}
