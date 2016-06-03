package com.wj.demo.ui.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wj.demo.R;
import com.wj.demo.domain.Item;

import java.util.ArrayList;

/**
 * 适配器
 * Created by wuj on 2016/6/1.
 * @version 1.0
 */
public class AdapterRecyclerItem extends RecyclerView.Adapter<ViewHolderItem> {

    private AppCompatActivity activity;
    private ArrayList<Item> items;

    public AdapterRecyclerItem(AppCompatActivity activity, ArrayList<Item> items){
        this.activity = activity;
        this.items = items;
    }


    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_item, parent, false);
        return new ViewHolderItem(v, activity);
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {
        holder.bindItem(items,position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
