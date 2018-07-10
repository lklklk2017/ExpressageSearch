package com.cdxxgc.expressagesearchdemo.DetailPage;

import com.cdxxgc.expressagesearchdemo.constract.DetailConstruct;

/**
 * Created by Administrator on 2018/3/22.
 */

public class DetailPresent implements DetailConstruct.P {

    private DetailConstruct.V view;
    private DetailConstruct.M model;

    public DetailPresent(DetailConstruct.V view) {
        this.view = view;
        model = new DetailModel();
    }


}
