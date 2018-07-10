package com.cdxxgc.expressagesearchdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21.
 * 物流信息返回bean类
 */
public class TrackInfo implements Parcelable {


    /**
     * LogisticCode : 7700046365395
     * ShipperCode : YD
     * Traces : [{"AcceptStation":"到达：福建平潭县公司保税区跨境电商分部 已揽件","AcceptTime":"2018-03-12 16:03:04"},{"AcceptStation":"到达：福建福清市公司 发往：四川成都网点包","AcceptTime":"2018-03-12 22:57:36"},{"AcceptStation":"到达：福建福清市公司 发往：福建晋江分拨中心","AcceptTime":"2018-03-13 00:27:23"},{"AcceptStation":"到达：福建晋江分拨中心","AcceptTime":"2018-03-13 03:03:47"},{"AcceptStation":"到达：福建晋江分拨中心 发往：四川成都分拨中心","AcceptTime":"2018-03-13 03:11:25"},{"AcceptStation":"到达：四川成都分拨中心 发往：四川成都双流县簇桥公司","AcceptTime":"2018-03-14 21:31:52"},{"AcceptStation":"到达：四川成都双流县簇桥公司 指定：任洁玲(15002853500) 派送","AcceptTime":"2018-03-15 10:21:36"},{"AcceptStation":"已签收 : 由本人 代签收，如有问题联系任洁玲(15002853500)","AcceptTime":"2018-03-15 13:12:53"}]
     * State : 3
     * EBusinessID : 1328204
     * Success : true
     */

    private String LogisticCode;
    private String ShipperCode;
    private String State;
    private String EBusinessID;
    private boolean Success;
    private List<TracesBean> Traces;

    protected TrackInfo(Parcel in) {
        LogisticCode = in.readString();
        ShipperCode = in.readString();
        State = in.readString();
        EBusinessID = in.readString();
        Success = in.readByte() != 0;
        Traces = in.createTypedArrayList(TracesBean.CREATOR);
    }

    public static final Creator<TrackInfo> CREATOR = new Creator<TrackInfo>() {
        @Override
        public TrackInfo createFromParcel(Parcel in) {
            return new TrackInfo(in);
        }

        @Override
        public TrackInfo[] newArray(int size) {
            return new TrackInfo[size];
        }
    };

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String LogisticCode) {
        this.LogisticCode = LogisticCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String ShipperCode) {
        this.ShipperCode = ShipperCode;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public List<TracesBean> getTraces() {
        return Traces;
    }

    public void setTraces(List<TracesBean> Traces) {
        this.Traces = Traces;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(LogisticCode);
        parcel.writeString(ShipperCode);
        parcel.writeString(State);
        parcel.writeString(EBusinessID);
        parcel.writeByte((byte) (Success ? 1 : 0));
        parcel.writeTypedList(Traces);
    }

    public static class TracesBean implements Parcelable {
        /**
         * AcceptStation : 到达：福建平潭县公司保税区跨境电商分部 已揽件
         * AcceptTime : 2018-03-12 16:03:04
         */

        private String AcceptStation;
        private String AcceptTime;

        protected TracesBean(Parcel in) {
            AcceptStation = in.readString();
            AcceptTime = in.readString();
        }

        public static final Creator<TracesBean> CREATOR = new Creator<TracesBean>() {
            @Override
            public TracesBean createFromParcel(Parcel in) {
                return new TracesBean(in);
            }

            @Override
            public TracesBean[] newArray(int size) {
                return new TracesBean[size];
            }
        };

        public String getAcceptStation() {
            return AcceptStation;
        }

        public void setAcceptStation(String AcceptStation) {
            this.AcceptStation = AcceptStation;
        }

        public String getAcceptTime() {
            return AcceptTime;
        }

        public void setAcceptTime(String AcceptTime) {
            this.AcceptTime = AcceptTime;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(AcceptStation);
            parcel.writeString(AcceptTime);
        }
    }
}
