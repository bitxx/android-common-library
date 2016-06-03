package com.wj.demo.domain.jsontest;


import com.wj.library.domain.Entity;

import java.util.ArrayList;

/**
 * 用于json解析示例
 * 货车父类型
 */
public class VechicleModel extends Entity {

    public static final int TYPE_1 = 1001;  //小面
    public static final int TYPE_2 = 1002;  //金杯
    public static final int TYPE_3 = 1003;  //依维柯
    public static final int TYPE_4 = 1004;  //箱4.2货车

    //车辆图片
    private int imgSrc;  //图片在本地的资源文件

    private int vehicleModelId;
    private String vehicleModelName;
    private float vehicleModelinitiatePrice;
    private ArrayList<VechicleType> vechicleTypelist;

    public int getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(int vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }

    public String getVehicleModelName() {
        return vehicleModelName;
    }

    public void setVehicleModelName(String vehicleModelName) {
        this.vehicleModelName = vehicleModelName;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public ArrayList<VechicleType> getVechicleTypelist() {
        return vechicleTypelist;
    }

    public void setVechicleTypelist(ArrayList<VechicleType> vechicleTypelist) {
        this.vechicleTypelist = vechicleTypelist;
    }

    public float getVehicleModelinitiatePrice() {
        return vehicleModelinitiatePrice;
    }

    public void setVehicleModelinitiatePrice(float vehicleModelinitiatePrice) {
        this.vehicleModelinitiatePrice = vehicleModelinitiatePrice;
    }
}
