package com.cdxxgc.expressagesearchdemo.utils;

import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/21.
 */
public interface APIservice {

    /**
     * http://api.kdniao.cc
     */
    String HOMEPATH = "http://api.kdniao.cc";

    /**
     * 获取物流信息
     * @param RequestType
     * @param EBusinessID
     * @param RequestData
     * @param DataSign
     * @param DataType
     * @return
     */
    @POST("/Ebusiness/EbusinessOrderHandle.aspx")
    Observable<TrackInfo> getTraceInfo(
            @Query("RequestType")String RequestType,
            @Query("EBusinessID")String EBusinessID,
            @Query("RequestData")String RequestData,
            @Query("DataSign")String DataSign,
            @Query("DataType")String DataType
    );
}