package com.cdxxgc.expressagesearchdemo.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.RecordsPage.RecordsView;
import com.cdxxgc.expressagesearchdemo.SelectPage.SelectPage;
import com.cdxxgc.expressagesearchdemo.TelPage.TelView;
import com.cdxxgc.expressagesearchdemo.base.BaseActivity;
import com.cdxxgc.expressagesearchdemo.constract.HomeConstruct;
import com.cdxxgc.expressagesearchdemo.entity.Company;
import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;
import com.cdxxgc.expressagesearchdemo.widget.CustomShowDialog;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * 首页视图层
 */
public class HomeView extends BaseActivity implements HomeConstruct.V, View.OnClickListener {

    private static final int REQUEST_TOSELECT = 001;
    private static final int REQUEST_TOQR = 002;
    private long lastTime = 0;

    private HomeConstruct.P mPresent;//逻辑实体
    private ImageView mImgScan;
    private TextView mTvComName;
    private EditText mEdEpressId;
    private Button mBtnSearch;
    private LinearLayout mLinSecond;
    private Company company;
    private LinearLayout mItemRecords;
    private LinearLayout mItemSearch;
    private LinearLayout mItemTel;
    private DrawerLayout mDrawer;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_TOSELECT:
                    company = data.getParcelableExtra("companyInfo");
                    mTvComName.setText(company.getName());
                    break;

                case REQUEST_TOQR:
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        Log.i("info", "二维码解析结果：" + result);
                        mEdEpressId.setText(result);
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(this, "扫描君表示没有看明白...再试一次吧", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresent = new HomePresent(this);
        inital();
    }

    /**
     * 查找控件
     */
    @Override
    public void findView() {
        mDrawer = ((DrawerLayout) findViewById(R.id.activity_main));//抽屉布局
        mTvComName = ((TextView) findViewById(R.id.home_comName_TV));//显示公司名称的控件
        mImgScan = ((ImageView) findViewById(R.id.home_scan_IMG));//扫描控件
        mEdEpressId = ((EditText) findViewById(R.id.home_id_ET));//id
        mBtnSearch = ((Button) findViewById(R.id.home_search_BTN));//搜索按钮
        mLinSecond = ((LinearLayout) findViewById(R.id.home_comName_Lin));//第二行组件

        //左布局元素
        mItemRecords = ((LinearLayout) findViewById(R.id.home_left_itemRecords_lin));
        mItemSearch = ((LinearLayout) findViewById(R.id.home_left_itemSearch_lin));
        mItemTel = ((LinearLayout) findViewById(R.id.home_left_itemTel_lin));
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
//        mDrawer.setDrawerShadow(R.drawable.user_slide, Gravity.LEFT);
    }

    /**
     * 绑定监听
     */
    @Override
    public void bindListener() {
        mImgScan.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
        mLinSecond.setOnClickListener(this);
        mItemRecords.setOnClickListener(this);
        mItemSearch.setOnClickListener(this);
        mItemTel.setOnClickListener(this);
    }

    /**
     * 各个控件的监听事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.home_scan_IMG://打开二维码扫描
                Intent qRIntent = new Intent(this, CaptureActivity.class);
                startActivityForResult(qRIntent, REQUEST_TOQR);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;

            case R.id.home_comName_Lin:
                Intent intent = new Intent(this, SelectPage.class);
                startActivityForResult(intent, REQUEST_TOSELECT);
                overridePendingTransition(R.anim.anim_in_a, R.anim.anim_out_a);
                break;

            case R.id.home_search_BTN:
                mPresent.track();
                break;

            case R.id.home_left_itemSearch_lin:
                toPage(HomeView.class);
                mDrawer.closeDrawers();
                overridePendingTransition(R.anim.anim_in_a, R.anim.anim_out_a);
                break;
            case R.id.home_left_itemRecords_lin:
                toPage(RecordsView.class);
                mDrawer.closeDrawers();
                overridePendingTransition(R.anim.anim_in_a, R.anim.anim_out_a);

                break;
            case R.id.home_left_itemTel_lin:
                toPage(TelView.class);
                mDrawer.closeDrawers();
                overridePendingTransition(R.anim.anim_in_a, R.anim.anim_out_a);

                break;
            default:
                break;
        }

    }

    /**
     * 获取快递单号
     *
     * @return
     */
    @Override
    public String getExpressId() {
        return mEdEpressId.getText().toString().trim();
    }

    @Override
    public String getCompCode() {
        if (company != null) {
            return company.getCompanyCode().toString().trim();
        }
        return null;
    }

    @Override
    public void toPageWithData(Class clzz, TrackInfo info) {
        Intent intent = new Intent(this, clzz);
        intent.putExtra("trackInfo", info);
        intent.putExtra("company", company);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_a, R.anim.anim_out_a);
    }

    /**
     * 获取公司信息
     *
     * @return
     */
    @Override
    public Company getCompanyInfo() {
        return company;
    }


    /**
     * 防止误接触
     */
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - lastTime <= 2000) {
            finish();
        } else {
            lastTime = System.currentTimeMillis();
            toast("再次按退格返回桌面哦~",false);
        }
    }
}
