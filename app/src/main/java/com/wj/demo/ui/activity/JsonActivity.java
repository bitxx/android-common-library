package com.wj.demo.ui.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.DialogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * json解析demo
 * Created by wuj on 2016/6/3.
 * @version 1.0
 */
public class JsonActivity extends BaseActivity {
    private static String TAG = JsonActivity.class.getSimpleName();

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
        JSONObject result = new JSONObject();
        JSONArray vechicleModelList = new JSONArray();  //存放多个父类车型

        //第一个父类车型，包括其子类车型
        JSONObject vechicleModel_1 = new JSONObject(); //父类车型
        JSONObject vechicleType_1_1 = new JSONObject(); //子类车型
        JSONArray vechicleModelList_1 = new JSONArray(); //存放子类车型的array

        //第二个父类车型，包括其子类车型
        JSONObject vechicleModel_2 = new JSONObject(); //父类车型
        JSONObject vechicleType_2_1 = new JSONObject(); //子类车型
        JSONArray vechicleModelList_2 = new JSONArray(); //存放子类车型的array

        //第三个父类车型，包括其子类车型
        JSONObject vechicleModel_3 = new JSONObject(); //父类车型
        JSONObject vechicleType_3_1 = new JSONObject(); //子类车型
        JSONArray vechicleModelList_3 = new JSONArray(); //存放子类车型的array

        try {
            result.put("successful",true);
            result.put("statusCode",1);
            result.put("statusInfo","车型获取成功");

            //存放第一个父类车型（包括其子类车型）
            vechicleModel_1.put("vehicleModelId",1001);
            vechicleModel_1.put("vehicleModelinitiatePrice",100.5);
            vechicleModel_1.put("vehicleModelName","父类车型1");
            vechicleType_1_1.put("vechicleTypeId",10011);
            vechicleType_1_1.put("vechicleTypeName","父类车型1下的第一个子类车型");
            vechicleType_1_1.put("initiatePrice",150.5);
            vechicleType_1_1.put("newEnergy",1);
            vechicleModelList_1.put(vechicleType_1_1);
            vechicleModel_1.put("vechicleTypelist",vechicleModelList_1);
            vechicleModelList.put(vechicleModel_1); //存放到顶层

            //存放第二个父类车型（包括其子类车型）
            vechicleModel_2.put("vehicleModelId",1002);
            vechicleModel_2.put("vehicleModelinitiatePrice",90.5);
            vechicleModel_2.put("vehicleModelName","父类车型2");
            vechicleType_2_1.put("vechicleTypeId",10021);
            vechicleType_2_1.put("vechicleTypeName","父类车型2下的第一个子类车型");
            vechicleType_2_1.put("initiatePrice",80.5);
            vechicleType_2_1.put("newEnergy",0);
            vechicleModelList_2.put(vechicleType_2_1);
            vechicleModel_2.put("vechicleTypelist",vechicleModelList_2);
            vechicleModelList.put(vechicleModel_2);

            //存放第三个父类车型（包括其子类车型）
            vechicleModel_3.put("vehicleModelId",1003);
            vechicleModel_3.put("vehicleModelinitiatePrice",190.5);
            vechicleModel_3.put("vehicleModelName","父类车型2");
            vechicleType_3_1.put("vechicleTypeId",10031);
            vechicleType_3_1.put("vechicleTypeName","父类车型2下的第一个子类车型");
            vechicleType_3_1.put("initiatePrice",180.5);
            vechicleType_3_1.put("newEnergy",0);
            vechicleModelList_3.put(vechicleType_2_1);
            vechicleModel_3.put("vechicleTypelist",vechicleModelList_3);
            vechicleModelList.put(vechicleModel_3);
            result.put("data",vechicleModelList);

            //Log.e(TAG,result.toString());

            DialogHelper.getMessageDialog(this, "json生成成功：\n" + result.toString(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析json
     */
    private void resolve(){

    }
}
