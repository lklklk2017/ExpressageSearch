package com.cdxxgc.expressagesearchdemo.utils;

import android.util.Log;
import android.widget.Toast;

import com.cdxxgc.expressagesearchdemo.MyApplication;

import rx.Observer;

/**
 * Created by Administrator on 2018/3/21.
 * 简单封装抽象类
 */
public abstract class CusResultCallBack<T> implements Observer<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Log.e("net_callback", "网络请求超时！ 当前线程是：" + Thread.currentThread().getName());
        onResultError(e);
    }

    @Override
    public void onNext(T o) {
        Log.i("net_callback", "网络请求成功！ 当前线程是：" + Thread.currentThread().getName());

        onResultSuccess(o);
    }

    /**
     * 网络请求成功回调
     *
     * @param o
     */
    public abstract void onResultSuccess(T o);

    /**
     * 网络请求失败回调
     *
     * @param e
     */
    public abstract void onResultError(Throwable e);

}