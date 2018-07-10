package com.cdxxgc.expressagesearchdemo.base;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cdxxgc.expressagesearchdemo.widget.CustomShowDialog;

/**
 * Created by Administrator on 2018/3/20.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Intent intent = new Intent();
    private CustomShowDialog mLoadingDialog;

    public void inital() {

        mLoadingDialog = new CustomShowDialog(this);
        findView();
        initView();
        bindListener();
    }

    public abstract void findView();

    public abstract void initView();

    public abstract void bindListener();

    public void toast(String content, boolean sure) {
        if (!sure) {
            Snackbar.make(getWindow().getDecorView(), content, Snackbar.LENGTH_LONG)
                    .show();
        }else{
            Snackbar.make(getWindow().getDecorView(), content, Snackbar.LENGTH_INDEFINITE)
                    .setAction("知道了", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        }
    }

    public void toPage(Class clzz) {
        intent = new Intent(this, clzz);
        startActivity(intent);
    }

    public void showDialog(boolean start) {
        if (start) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }

    }

}
