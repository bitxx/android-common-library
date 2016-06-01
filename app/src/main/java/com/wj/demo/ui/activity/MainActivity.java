package com.wj.demo.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.wj.demo.R;
import com.wj.demo.domain.Item;
import com.wj.demo.ui.adapter.AdapterRecyclerItem;
import com.wj.demo.ui.base.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static String TAG = MainActivity.class.getName();

    private RecyclerView rvItem;
    private AdapterRecyclerItem adapterRecyclerItem;
    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        rvItem = (RecyclerView)findViewById(R.id.rv_item);

        Log.e(TAG,items.size()+"");
        //地址动态添加
        adapterRecyclerItem = new AdapterRecyclerItem(this, items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager);
        rvItem.setAdapter(adapterRecyclerItem);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "idea_wj@163.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        items = new ArrayList<>();

        Item item1 = new Item();
        item1.setName("1.JSON解析");
        items.add(item1);

        Item item2 = new Item();
        item2.setName("2.Socket相关");
        items.add(item2);

        Item item3 = new Item();
        item3.setName("3.图片处理");
        items.add(item3);

        Item item4 = new Item();
        item4.setName("4.暂未开放");
        items.add(item4);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
