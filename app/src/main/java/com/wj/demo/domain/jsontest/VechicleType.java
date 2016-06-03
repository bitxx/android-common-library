package com.wj.demo.domain.jsontest;


import com.wj.library.domain.Entity;

/**
 * 用于json解析示例
 * 货车子类型
 */
public class VechicleType extends Entity {
    public static String IS_NEW_ENERGY = "Y";
    public static String IS_NOT_NEW_ENERGY = "N";

    private int vechicleTypeId;
    private String vechicleTypeName;
    private String newEnergy;  //是否为新能源车
    private float initiatePrice;   //起步价

    private boolean isChecked = false;  //是否被选中,本地数据,默认均未被选中

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getVechicleTypeId() {
        return vechicleTypeId;
    }

    public void setVechicleTypeId(int vechicleTypeId) {
        this.vechicleTypeId = vechicleTypeId;
    }

    public String getVechicleTypeName() {
        return vechicleTypeName;
    }

    public void setVechicleTypeName(String vechicleTypeName) {
        this.vechicleTypeName = vechicleTypeName;
    }

    public String getNewEnergy() {
        return newEnergy;
    }

    public void setNewEnergy(String newEnergy) {
        this.newEnergy = newEnergy;
    }

    public float getInitiatePrice() {
        return initiatePrice;
    }

    public void setInitiatePrice(float initiatePrice) {
        this.initiatePrice = initiatePrice;
    }
}