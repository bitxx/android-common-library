package com.wj.demo.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wj.demo.R;
import com.wj.demo.ui.base.BaseActivity;
import com.wj.library.helper.DialogHelper;
import com.wj.library.helper.ToastHelper;
import com.wj.library.helper.ToolbarHelper;
import com.wj.library.helper.UIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * json解析demo
 * 该demo仅仅是用来展示java原生提供的几个json类的使用方法，并没有单独封装
 * Created by wuj on 2016/6/3.
 * @version 1.0
 */
public class JsonDemoActivity extends BaseActivity {
    private static String TAG = JsonDemoActivity.class.getSimpleName();

    private TextView tvJson;
    private Button btCreate;  //生成
    private Button btResolve;  //解析
    private Toolbar toolbar;
    private TextView tvTitle;

    private JSONObject result;  //整个json解析和生成都将依托它

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        initData();
        initView();
    }

    @Override
    protected void initData() {
        super.initData();
        result = new JSONObject();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DialogHelper.getMessageDialog(this, "当前页面demo仅仅是用来展示java原生提供的几个json类的使用方法，并没有单独封装", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }

    @Override
    protected void initView() {
        super.initView();
        tvJson = (TextView) findViewById(R.id.tv_json);
        btCreate = (Button) findViewById(R.id.bt_create);
        btResolve = (Button) findViewById(R.id.bt_resolve);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvTitle = (TextView)findViewById(R.id.tv_title);

        tvTitle.setText("JSON生成与解析");
        btCreate.setOnClickListener(this);
        btResolve.setOnClickListener(this);
        ToolbarHelper.initToolbar(this,toolbar,R.mipmap.ic_back);

        String json = "{\n" +
                "    \"successful\": true,\n" +
                "    \"statusCode\": \"1\",\n" +
                "    \"statusInfo\": \"操作成功\",\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"vechicleTypelist\": [\n" +
                "                {\n" +
                "                    \"vechicleTypeId\": 1,\n" +
                "                    \"vechicleTypeName\": \"小面新能源\",\n" +
                "                    \"newEnergy\": \"Y\",\n" +
                "                    \"initiatePrice\": 28.0\n" +
                "                }\n" +
                "            ],\n" +
                "            \"vehicleModelId\": 1001,\n" +
                "            \"vehicleModelinitiatePrice\": 28.0,\n" +
                "            \"vehicleModelName\": \"小面\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vechicleTypelist\": [\n" +
                "                \n" +
                "            ],\n" +
                "            \"vehicleModelId\": 1002,\n" +
                "            \"vehicleModelinitiatePrice\": 48.0,\n" +
                "            \"vehicleModelName\": \"金杯\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"vechicleTypelist\": [\n" +
                "                \n" +
                "            ],\n" +
                "            \"vehicleModelId\": 1004,\n" +
                "            \"vehicleModelinitiatePrice\": 95.0,\n" +
                "            \"vehicleModelName\": \"4.2米箱货\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"page\": null\n" +
                "}{\n" +
                "    \"vechicleTypelist\": [\n" +
                "        {\n" +
                "            \"vechicleTypeId\": 1,\n" +
                "            \"vechicleTypeName\": \"小面新能源\",\n" +
                "            \"newEnergy\": \"Y\",\n" +
                "            \"initiatePrice\": 28.0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"vehicleModelId\": 1001,\n" +
                "    \"vehicleModelinitiatePrice\": 28.0,\n" +
                "    \"vehicleModelName\": \"小面\"\n" +
                "}";
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
                parser();
                break;
        }
    }

    /**
     * 生成json
     */
    private void create(){

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
            vechicleModelList_3.put(vechicleType_3_1);
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
    private void parser(){
        try {
            JSONTokener parser = new JSONTokener(result.toString());
            JSONObject res = (JSONObject) parser.nextValue();  //也就是开始第一个大括号
            String all = "\n\nsuccessful："+res.getBoolean("successful")
                    + "\nstatusCode:"+res.getInt("statusCode")
                    + "\nstatusInfo:"+res.getString("statusInfo");
            JSONArray vechicleModelList = res.getJSONArray("data");

            //解析第一类父车型（包括子类车型）
            JSONObject vechicleModel_1 = vechicleModelList.getJSONObject(0);
            JSONObject vechicleType_1_1 = new JSONObject(); //子类车型
            JSONArray vechicleModelList_1 = new JSONArray(); //存放子类车型的array
            all  += "\n\nvehicleModelId:" +vechicleModel_1.getInt("vehicleModelId")
                    + "\nvehicleModelinitiatePrice:"+vechicleModel_1.getDouble("vehicleModelinitiatePrice")
                    + "\nvehicleModelName:"+vechicleModel_1.getString("vehicleModelName");
            vechicleModelList_1 = vechicleModel_1.getJSONArray("vechicleTypelist");
            vechicleType_1_1 = vechicleModelList_1.getJSONObject(0);
            all += "\nvechicleTypeId:"+vechicleType_1_1.getInt("vechicleTypeId")
                    + "\nvechicleTypeName:"+vechicleType_1_1.getString("vechicleTypeName")
                    + "\ninitiatePrice:"+vechicleType_1_1.getDouble("initiatePrice")
                    + "\nnewEnergy:"+vechicleType_1_1.getInt("newEnergy");


            //解析第二类父车型（包括子类车型）
            JSONObject vechicleModel_2 = vechicleModelList.getJSONObject(1); //注意此处变化，index变为1
            JSONObject vechicleType_2_1 = new JSONObject(); //子类车型
            JSONArray vechicleModelList_2 = new JSONArray(); //存放子类车型的array
            all  += "\n\nvehicleModelId:" +vechicleModel_2.getInt("vehicleModelId")
                    + "\nvehicleModelinitiatePrice:"+vechicleModel_2.getDouble("vehicleModelinitiatePrice")
                    + "\nvehicleModelName:"+vechicleModel_2.getString("vehicleModelName");
            vechicleModelList_2 = vechicleModel_2.getJSONArray("vechicleTypelist");
            vechicleType_2_1 = vechicleModelList_2.getJSONObject(0);
            all += "\nvechicleTypeId:"+vechicleType_2_1.getInt("vechicleTypeId")
                    + "\nvechicleTypeName:"+vechicleType_2_1.getString("vechicleTypeName")
                    + "\ninitiatePrice:"+vechicleType_2_1.getDouble("initiatePrice")
                    + "\nnewEnergy:"+vechicleType_2_1.getInt("newEnergy");

            //解析第三类父车型（包括子类车型）
            JSONObject vechicleModel_3 = vechicleModelList.getJSONObject(2); //注意此处变化，index变为2
            JSONObject vechicleType_3_1 = new JSONObject(); //子类车型
            JSONArray vechicleModelList_3 = new JSONArray(); //存放子类车型的array
            all  += "\n\nvehicleModelId:" +vechicleModel_3.getInt("vehicleModelId")
                    + "\nvehicleModelinitiatePrice:"+vechicleModel_3.getDouble("vehicleModelinitiatePrice")
                    + "\nvehicleModelName:"+vechicleModel_3.getString("vehicleModelName");
            vechicleModelList_3 = vechicleModel_3.getJSONArray("vechicleTypelist");
            vechicleType_3_1 = vechicleModelList_3.getJSONObject(0);
            all += "\nvechicleTypeId:"+vechicleType_3_1.getInt("vechicleTypeId")
                    + "\nvechicleTypeName:"+vechicleType_3_1.getString("vechicleTypeName")
                    + "\ninitiatePrice:"+vechicleType_3_1.getDouble("initiatePrice")
                    + "\nnewEnergy:"+vechicleType_3_1.getInt("newEnergy");

            DialogHelper.getMessageDialog(this, "json解析成功：\n" + all, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        } catch (JSONException e) {
            ToastHelper.toastShort(this,"请先生成json");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                UIHelper.activityFinish(this);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
