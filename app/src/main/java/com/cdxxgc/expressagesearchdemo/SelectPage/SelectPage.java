package com.cdxxgc.expressagesearchdemo.SelectPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.cdxxgc.expressagesearchdemo.HomePage.HomeView;
import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.adapter.SelectAdatper;
import com.cdxxgc.expressagesearchdemo.base.BaseActivity;
import com.cdxxgc.expressagesearchdemo.entity.Company;
import com.cdxxgc.expressagesearchdemo.utils.PULLParserTool;
import com.cdxxgc.expressagesearchdemo.widget.CustomItemDecoration;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SelectPage extends BaseActivity implements OnClickListener, SelectAdatper.OnItemClickListener {

    private ImageView mImgBack;
    private RecyclerView mRcy;
    private SelectAdatper adapter;
    private ArrayList<Company> mCompanyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_page);
        inital();
    }

    @Override
    public void findView() {
        mImgBack = ((ImageView) findViewById(R.id.select_back_IMG));
        mRcy = ((RecyclerView) findViewById(R.id.select_rcy));
    }

    @Override
    public void initView() {
        mRcy.setLayoutManager(new LinearLayoutManager(this));
        mRcy.addItemDecoration(new CustomItemDecoration());
        adapter = new SelectAdatper(this);
        mRcy.setAdapter(adapter);
        initData();//初始化数据
        adapter.setData(mCompanyList);
    }

    /**
     * 初始化公司的数据
     */
    private void initData() {
        mCompanyList = PULLParserTool.getCompList();
        /**
         * 打算使用xml将数据解析导入，但是由于图片资源引用有问题，只能采用动态加载数据了
         */
//        try {
//            InputStream is = getAssets().open("data.xml");
//            mCompanyList = PULLParserTool.getComList(is);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void bindListener() {
        mImgBack.setOnClickListener(this);
        adapter.setOnItemListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.select_back_IMG:
                finish();
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;

            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        Log.i("info", "位置：" + position);
        Intent intent = new Intent(this, HomeView.class);
        intent.putExtra("companyInfo", mCompanyList.get(position));
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }
}
