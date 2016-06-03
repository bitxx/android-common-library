package com.wj.demo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;

/**
 * json解析demo
 * Created by wuj on 2016/6/3.
 * @version 1.0
 */
public class JsonActivity extends BaseActivity {

    private TextView tvJson;
    private Button btCreate;  //生成
    private Button btResolve;  //解析

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        initData();
        initView();
    }


    @Override
    protected void initView() {
        super.initView();
        tvJson = (TextView) findViewById(R.id.tv_json);
        btCreate = (Button) findViewById(R.id.bt_create);
        btResolve = (Button) findViewById(R.id.bt_resolve);

        btCreate.setOnClickListener(this);
        btResolve.setOnClickListener(this);
        String json = "{\"successful\":true,\"statusCode\":\"1\",\"statusInfo\":\"操作成功\",\"data\":[{\"vechicleTypelist\":[{\"vechicleTypeId\":1,\"vechicleTypeName\":\"小面新能源\",\"newEnergy\":\"Y\",\"initiatePrice\":28.0}],\"vehicleModelId\":1001,\"vehicleModelinitiatePrice\":28.0,\"vehicleModelName\":\"小面\"},{\"vechicleTypelist\":[],\"vehicleModelId\":1002,\"vehicleModelinitiatePrice\":48.0,\"vehicleModelName\":\"金杯\"},{\"vechicleTypelist\":[],\"vehicleModelId\":1004,\"vehicleModelinitiatePrice\":95.0,\"vehicleModelName\":\"4.2米箱货\"}],\"page\":null}\n";
        tvJson.setText(json);
    }

    @Override
    protected void myOnClick(View view) {
        super.myOnClick(view);
        switch(view.getId()){
            case R.id.bt_create:
                create();
                break;
            case R.id.bt_resolve:
                resolve();
                break;
        }
    }

    /**
     * 生成json
     */
    private void create(){

    }

    /**
     * 解析json
     */
    private void resolve(){

    }
}
