package com.cdxxgc.expressagesearchdemo.entity;

/**
 * Created by Administrator on 2018/3/21.
 */

public class PostDataBean {
    private String requestType;
    private String eBusinessID;
    private String requestData;
    private String dataSign;
    private String dataType;

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String geteBusinessID() {
        return eBusinessID;
    }

    public void seteBusinessID(String eBusinessID) {
        this.eBusinessID = eBusinessID;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getDataSign() {
        return dataSign;
    }

    public void setDataSign(String dataSign) {
        this.dataSign = dataSign;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "PostDataBean{" +
                "requestType='" + requestType + '\'' +
                ", eBusinessID='" + eBusinessID + '\'' +
                ", requestData='" + requestData + '\'' +
                ", dataSign='" + dataSign + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

    public PostDataBean(String requestType, String eBusinessID, String requestData, String dataSign, String dataType) {
        this.requestType = requestType;
        this.eBusinessID = eBusinessID;
        this.requestData = requestData;
        this.dataSign = dataSign;
        this.dataType = dataType;
    }

    public PostDataBean() {
    }
}
