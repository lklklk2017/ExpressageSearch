package com.cdxxgc.expressagesearchdemo.widget;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cdxxgc.expressagesearchdemo.MyApplication;
import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.base.BaseDialog;

/**
 * Created by Administrator on 2018/4/6.
 */

public class CustomShowDialog extends BaseDialog {

    private ImageView mImg;

    public CustomShowDialog(Context context, Boolean canTouchCancel, int layoutResId) {
        super(context, canTouchCancel, layoutResId);

    }

    public CustomShowDialog(Context context) {
        super(context, false, R.layout.dialog_during);

    }

    public CustomShowDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void initWindow() {
    }
}
