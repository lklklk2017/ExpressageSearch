package com.cdxxgc.expressagesearchdemo.TelPage;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.adapter.TelAdapter;
import com.cdxxgc.expressagesearchdemo.base.BaseActivity;
import com.cdxxgc.expressagesearchdemo.entity.Company;
import com.cdxxgc.expressagesearchdemo.utils.PULLParserTool;
import com.cdxxgc.expressagesearchdemo.widget.CustomItemDecoration;

import java.util.ArrayList;

/**
 * 电话簿
 * view
 */
public class TelView extends BaseActivity implements TelAdapter.ItemClickListener {

    private RecyclerView mRcy;
    private ArrayList<Company> mCompList;
    private TelAdapter mAdapter;
    private static final int REQUEST_CODE_CALLPHONE = 0x01;
    private String tel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_view);
        inital();
    }

    @Override
    public void findView() {
        mRcy = ((RecyclerView) findViewById(R.id.tel_rcy));
    }

    @Override
    public void initView() {
        mRcy.setLayoutManager(new LinearLayoutManager(this));
        mCompList = PULLParserTool.getCompList();
        mAdapter = new TelAdapter(mCompList);
        mRcy.setAdapter(mAdapter);
        mRcy.addItemDecoration(new CustomItemDecoration());
    }

    @Override
    public void bindListener() {

        mAdapter.setItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Company company = mCompList.get(position);
        tel = company.getTel();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                //用户已经拒绝
                Toast.makeText(this, "请打开设置-权限 中进行授权设置", Toast.LENGTH_SHORT).show();

            } else {
                //开始执行请求
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALLPHONE);

            }
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_CALLPHONE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //已授权
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel)));

                } else {
                    //未授权
                    Toast.makeText(this, "请打开设置-权限 中进行授权设置", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);

    }
}
