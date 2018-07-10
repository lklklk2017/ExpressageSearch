package com.cdxxgc.expressagesearchdemo.widget;

import android.content.Context;
import android.view.View;

import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.base.BaseDialog;

/**
 * Created by Administrator on 2018/4/6.
 */

public class CustomSelectDialog extends BaseDialog implements View.OnClickListener {

    private DialogClickLitener listener;

    public void setDialogListener(DialogClickLitener listener) {
        this.listener = listener;
    }

    public CustomSelectDialog(Context context, Boolean canTouchCancel, int layoutResId) {
        super(context, canTouchCancel, layoutResId);
    }

    public CustomSelectDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void initWindow() {
        View view = getWindow().getDecorView();
        view.findViewById(R.id.dialog_sel1_tv).setOnClickListener(this);
        view.findViewById(R.id.dialog_sel2_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_sel1_tv:
                if (listener != null) {
                    listener.onCallPhone();
                }
                break;
            case R.id.dialog_sel2_tv:
                if (listener != null) {
                    listener.onDeletRecord();
                }
                break;
        }
    }

    public interface DialogClickLitener {
        void onCallPhone();

        void onDeletRecord();
    }
}
