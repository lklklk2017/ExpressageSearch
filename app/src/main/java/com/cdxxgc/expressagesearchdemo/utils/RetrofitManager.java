package com.cdxxgc.expressagesearchdemo.utils;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/3/20.
 * 网络管理器(RxJava+Retrofit)
 * 单例模式
 */
public class RetrofitManager {

    private static Retrofit mRetrofit = null;

    private RetrofitManager() throws Exception {
        throw new Exception("can not invoke it by this method");
    }

    /**
     * 获取retrofit实体类型
     *
     * @return
     */
    public static Retrofit getInstence() {

        if (mRetrofit == null) {
            /*加锁原因：当retrofit没有实例的时候，防止第一次初始化时候，第一个进程还没初始化完毕（判空）
            就执行了接下来的进程的实例化该实例*/
            synchronized (RetrofitManager.class) {
                if (mRetrofit == null) {
                    return mRetrofit = new Retrofit.Builder()
                            /*指定根路径*/
                            .baseUrl(APIservice.HOMEPATH)
                            /*指定实体解析类型*/
                            .addConverterFactory(GsonConverterFactory.create())
                            /*指定响应模式为observable类,支持RxJava*/
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return mRetrofit;
    }

    /**
     * 获取ApiService
     *
     * @return
     */
    public static APIservice getApiService() {
        return getInstence().create(APIservice.class);
    }
}
