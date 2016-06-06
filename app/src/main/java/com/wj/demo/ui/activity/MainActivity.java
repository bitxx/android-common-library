package com.wj.demo.ui.activity;

import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.wj.demo.R;
import com.wj.demo.domain.Item;
import com.wj.demo.ui.adapter.AdapterRecyclerItem;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.ToastHelper;
import com.wj.library.helper.ToolbarHelper;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private static String TAG = MainActivity.class.getName();

    private RecyclerView rvItem;
    private Toolbar toolbar;
    private AdapterRecyclerItem adapterRecyclerItem;
    private ArrayList<Item> items;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        rvItem = (RecyclerView)findViewById(R.id.rv_item);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //地址动态添加
        adapterRecyclerItem = new AdapterRecyclerItem(this, items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvItem.setLayoutManager(linearLayoutManager);
        rvItem.setAdapter(adapterRecyclerItem);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "idea_wj@163.com", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ToolbarHelper.initToolbar(this,toolbar,R.string.app_name,R.mipmap.ic_drawer_home,Color.WHITE);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_search) {
                    ToastHelper.toastShort(MainActivity.this,R.string.menu_search);

                } else if (menuItemId == R.id.action_notification) {
                    ToastHelper.toastShort(MainActivity.this,R.string.menu_notifications);

                } else if (menuItemId == R.id.action_settings) {
                    ToastHelper.toastShort(MainActivity.this,R.string.item_setting);

                } else if (menuItemId == R.id.action_about) {
                    ToastHelper.toastShort(MainActivity.this,R.string.item_about);

                }
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        items = new ArrayList<>();

        Item item1 = new Item();
        item1.setName("1.JSON生成与解析");
        item1.setActivity(JsonDemoActivity.class);
        items.add(item1);

        Item item2 = new Item();
        item2.setName("2.Socket相关");
        items.add(item2);

        Item item3 = new Item();
        item3.setName("3.图片处理");
        items.add(item3);

        Item item4 = new Item();
        item4.setName("4.有弹性的ScrollView");
        item4.setActivity(ElasticScorllViewDemoActivity.class);
        items.add(item4);

        Item item5 = new Item();
        item5.setName("5.页面手势滑动");
        items.add(item5);

        Item item6 = new Item();
        item6.setName("6.暂未开放");
        items.add(item6);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                break;
        }

        return false;
    }
}
