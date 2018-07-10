package com.cdxxgc.expressagesearchdemo.DetailPage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.adapter.DetailAdapter;
import com.cdxxgc.expressagesearchdemo.base.BaseActivity;
import com.cdxxgc.expressagesearchdemo.constract.DetailConstruct;
import com.cdxxgc.expressagesearchdemo.entity.Company;
import com.cdxxgc.expressagesearchdemo.entity.Recorde;
import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DetailView extends BaseActivity implements DetailConstruct.V, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private DetailConstruct.P mPresent;
    private ImageView mImgBack;
    private TextView mTvCompName;
    private TextView mTvExpressId;
    private TextView mTvTime;//总耗时
    private RecyclerView mRcy;
    private SwipeRefreshLayout mSwip;
    private DetailAdapter adapter;
    private SimpleDateFormat mSdf;
    private ImageView mImgCom;
    private List<TrackInfo.TracesBean> traces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        mPresent = new DetailPresent(this);
        inital();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        TrackInfo trackInfo = intent.getParcelableExtra("trackInfo");
        Company company = intent.getParcelableExtra("company");
        Recorde recorde = intent.getParcelableExtra("record");
        mTvTime.setText("耗时 天 小时");

        if (trackInfo != null) {
            mTvExpressId.setText(trackInfo.getLogisticCode());
            traces = trackInfo.getTraces();
            if (traces != null) {
                //2018-03-21 21:08:27

                adapter.setData(traces);
                initDuringTime(traces);

            } else {
                toast("查找君没有找到轨迹信息..-_-||", false);
            }
        }

        if (company != null) {
            mTvCompName.setText(company.getName());
            mImgCom.setImageResource(company.getRes_imgId());
        }

        if (recorde != null) {
            mTvCompName.setText(recorde.getCompName());
            mImgCom.setImageResource(recorde.getRes_imgId());
        }
    }

    /**
     * 计算总耗时
     *
     * @param traces
     */
    private void initDuringTime(List<TrackInfo.TracesBean> traces) {
        mSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String firstTime = traces.get(0).getAcceptTime();
        String lastTime = traces.get(traces.size() - 1).getAcceptTime();
        try {
            long first_date = mSdf.parse(firstTime).getTime();
            long last_date = mSdf.parse(lastTime).getTime();
            long time_lag = last_date - first_date;
            int days = (int) (time_lag / (3600 * 24 * 1000));
            long hours = (time_lag - (days * 3600 * 24 * 1000)) / (3600 * 1000);

            Log.i("info", "差时:" + days + "天" + hours + "小时");
            mTvTime.setText("耗时:" + days + "天" + hours + "小时");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findView() {
        mImgBack = ((ImageView) findViewById(R.id.detail_back_IMG));
        mImgCom = ((ImageView) findViewById(R.id.detail_headProt_IMG));
        mTvCompName = ((TextView) findViewById(R.id.detail_compName_TV));
        mTvExpressId = ((TextView) findViewById(R.id.detail_expressId_TV));
        mTvTime = ((TextView) findViewById(R.id.detail_sumtime_TV));
        mRcy = ((RecyclerView) findViewById(R.id.detail_rcy));
        mSwip = ((SwipeRefreshLayout) findViewById(R.id.detail_swipe));//刷新控件
    }

    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRcy.setLayoutManager(linearLayoutManager);
        mRcy.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new DetailAdapter();
        mRcy.setAdapter(adapter);
        mSwip.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW);
    }

    @Override
    public void bindListener() {
        mImgBack.setOnClickListener(this);
        mSwip.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.detail_back_IMG:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 下刷新时候的逻辑
     */
    @Override
    public void onRefresh() {

        setIsrefresh(false);
        toast("别拖啦，数据已经最新啦~", false);

    }

    @Override
    public void setIsrefresh(boolean isrefresh) {
        mSwip.setRefreshing(isrefresh);
    }
}
