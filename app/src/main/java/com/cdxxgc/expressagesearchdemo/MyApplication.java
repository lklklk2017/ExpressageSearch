package com.cdxxgc.expressagesearchdemo;

import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by Administrator on 2018/3/21.
 */

public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        //使用二维码扫描的功能初始化
        ZXingLibrary.initDisplayOpinion(this);
    }

    /**
     * 获取全局的context变量
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
