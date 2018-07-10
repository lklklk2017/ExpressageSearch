package com.cdxxgc.expressagesearchdemo.base;

import android.app.Dialog;
import android.content.Context;

import com.cdxxgc.expressagesearchdemo.R;

/**
 * Created by Administrator on 2018/4/6.
 */

public abstract class BaseDialog extends Dialog {


    public BaseDialog(Context context, Boolean canTouchCancel, int layoutResId) {
        super(context, R.style.CustomDialogStyle);
        setCanceledOnTouchOutside(canTouchCancel);
        setContentView(layoutResId);
        initWindow();
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected abstract void initWindow();
}
