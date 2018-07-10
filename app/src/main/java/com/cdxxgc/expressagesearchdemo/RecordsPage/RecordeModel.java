package com.cdxxgc.expressagesearchdemo.RecordsPage;

import com.cdxxgc.expressagesearchdemo.constract.HomeConstruct;
import com.cdxxgc.expressagesearchdemo.constract.RecordeConstruct;
import com.cdxxgc.expressagesearchdemo.entity.PostDataBean;
import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;
import com.cdxxgc.expressagesearchdemo.utils.CusResultCallBack;
import com.cdxxgc.expressagesearchdemo.utils.RetrofitManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/4.
 */

public class RecordeModel implements RecordeConstruct.M {
    public RecordeModel() {
    }

    @Override
    public void getRecordeInfo() {

    }

    @Override
    public void search(PostDataBean dataBean, final RecordeConstruct.M.TrackHttpResult result) {
        RetrofitManager.getApiService().getTraceInfo(dataBean.getRequestType(),
                dataBean.geteBusinessID(),
                dataBean.getRequestData(),
                dataBean.getDataSign(),
                dataBean.getDataType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CusResultCallBack<TrackInfo>() {
                    @Override
                    public void onResultSuccess(TrackInfo o) {
                        if (o.isSuccess()) {
                            result.success(o);
                        } else {
                            result.error("get information failed!");
                        }
                    }

                    @Override
                    public void onResultError(Throwable e) {
                        //网络连接错误的提示已经封装，不必在此做出判断
                        result.error("网络超时，请检查网络是否连接正常");
                    }
                });
    }
}
