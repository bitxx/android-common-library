package com.wj.demo.domain.jsontest;


import com.wj.library.domain.Entity;

import java.util.ArrayList;

/**
 * 用于json解析示例
 * @author idea_wj
 */
public class ResultVechicleModel extends Entity {
    private String status;
    private boolean successful;
    private String statusCode;
    private String statusInfo;
    private String page;
    private ArrayList<VechicleModel> data;

    public ArrayList<VechicleModel> getData() {
        return data;
    }

    public void setData(ArrayList<VechicleModel> data) {
        this.data = data;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
