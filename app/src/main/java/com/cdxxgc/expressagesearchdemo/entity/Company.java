package com.cdxxgc.expressagesearchdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/3/22.
 * post提交数据bean
 */

public class Company implements Parcelable {

    private String name;
    private int res_imgId;
    private String tel;
    private String companyCode;

    protected Company(Parcel in) {
        name = in.readString();
        res_imgId = in.readInt();
        tel = in.readString();
        companyCode = in.readString();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRes_imgId() {
        return res_imgId;
    }

    public void setRes_imgId(int res_imgId) {
        this.res_imgId = res_imgId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", res_imgId=" + res_imgId +
                ", tel='" + tel + '\'' +
                ", companyCode='" + companyCode + '\'' +
                '}';
    }

    public Company(String name, int res_imgId, String tel, String companyCode) {
        this.name = name;
        this.res_imgId = res_imgId;
        this.tel = tel;
        this.companyCode = companyCode;
    }

    public Company(String name, int res_imgId, String tel) {
        this.name = name;
        this.res_imgId = res_imgId;
        this.tel = tel;
    }

    public Company() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(res_imgId);
        parcel.writeString(tel);
        parcel.writeString(companyCode);
    }
}
