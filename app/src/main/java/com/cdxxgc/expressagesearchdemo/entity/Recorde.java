package com.cdxxgc.expressagesearchdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2018/4/1.
 * 本地数据记录Bean
 */
@Entity
public class Recorde implements Parcelable {

    @Id
    private long LogisticCode;//运单编号
    @NotNull
    @Property(nameInDb = "t_comName")
    private String compName;//公司名称
    @NotNull
    @Property(nameInDb = "t_imgId")
    private int res_imgId;//图片id
    @NotNull
    @Property(nameInDb = "t_comTel")
    private String ComTel;//公司电话
    @NotNull
    @Property(nameInDb = "t_lastState")
    private String lastAcceptStation;//最近状态
    @NotNull
    @Property(nameInDb = "t_lastTime")
    private String lastAcceptTime;//最近时间
    @NotNull
    @Property(nameInDb = "t_comCode")
    private String ComCode;//公司编号

    @Generated(hash = 1814179077)
    public Recorde(long LogisticCode, @NotNull String compName, int res_imgId,
                   @NotNull String ComTel, @NotNull String lastAcceptStation,
                   @NotNull String lastAcceptTime, @NotNull String ComCode) {
        this.LogisticCode = LogisticCode;
        this.compName = compName;
        this.res_imgId = res_imgId;
        this.ComTel = ComTel;
        this.lastAcceptStation = lastAcceptStation;
        this.lastAcceptTime = lastAcceptTime;
        this.ComCode = ComCode;
    }

    @Generated(hash = 1351485090)
    public Recorde() {
    }

    protected Recorde(Parcel in) {
        LogisticCode = in.readLong();
        compName = in.readString();
        res_imgId = in.readInt();
        ComTel = in.readString();
        lastAcceptStation = in.readString();
        lastAcceptTime = in.readString();
        ComCode = in.readString();
    }

    public static final transient Creator<Recorde> CREATOR = new Creator<Recorde>() {
        @Override
        public Recorde createFromParcel(Parcel in) {
            return new Recorde(in);
        }

        @Override
        public Recorde[] newArray(int size) {
            return new Recorde[size];
        }
    };

    @Override
    public String toString() {
        return "Recorde{" +
                "LogisticCode=" + LogisticCode +
                ", compName='" + compName + '\'' +
                ", res_imgId=" + res_imgId +
                ", ComTel='" + ComTel + '\'' +
                ", lastAcceptStation='" + lastAcceptStation + '\'' +
                ", lastAcceptTime='" + lastAcceptTime + '\'' +
                '}';
    }

    public String getComCode() {
        return this.ComCode;
    }

    public void setComCode(String ComCode) {
        this.ComCode = ComCode;
    }

    public String getLastAcceptTime() {
        return this.lastAcceptTime;
    }

    public void setLastAcceptTime(String lastAcceptTime) {
        this.lastAcceptTime = lastAcceptTime;
    }

    public String getLastAcceptStation() {
        return this.lastAcceptStation;
    }

    public void setLastAcceptStation(String lastAcceptStation) {
        this.lastAcceptStation = lastAcceptStation;
    }

    public String getComTel() {
        return this.ComTel;
    }

    public void setComTel(String ComTel) {
        this.ComTel = ComTel;
    }

    public int getRes_imgId() {
        return this.res_imgId;
    }

    public void setRes_imgId(int res_imgId) {
        this.res_imgId = res_imgId;
    }

    public String getCompName() {
        return this.compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public long getLogisticCode() {
        return this.LogisticCode;
    }

    public void setLogisticCode(long LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(LogisticCode);
        dest.writeString(compName);
        dest.writeInt(res_imgId);
        dest.writeString(ComTel);
        dest.writeString(lastAcceptStation);
        dest.writeString(lastAcceptTime);
        dest.writeString(ComCode);
    }
}
