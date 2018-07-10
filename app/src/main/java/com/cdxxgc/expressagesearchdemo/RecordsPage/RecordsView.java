package com.cdxxgc.expressagesearchdemo.RecordsPage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.adapter.RecordeAdapter;
import com.cdxxgc.expressagesearchdemo.base.BaseActivity;
import com.cdxxgc.expressagesearchdemo.constract.RecordeConstruct;
import com.cdxxgc.expressagesearchdemo.entity.Recorde;
import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;
import com.cdxxgc.expressagesearchdemo.utils.DBManager;
import com.cdxxgc.expressagesearchdemo.widget.CustomItemDecoration;
import com.cdxxgc.expressagesearchdemo.widget.CustomSelectDialog;

import java.util.List;

/**
 * 查询记录
 * view
 */
public class RecordsView extends BaseActivity implements RecordeConstruct.V,
        RecordeAdapter.RecodesClickLisener,
        RecordeAdapter.RecodesLongClickLisener,
        CustomSelectDialog.DialogClickLitener, RecordeAdapter.OnBlankCallBack {

    private static final int REQUEST_CODE_CALLPHONE = 0x01;
    private RecyclerView mRcy;
    private RecordeAdapter mAdapter;
    private RecordePresenter mPresenter;
    private Recorde recorde;
    private CustomSelectDialog mCustomDialog;
    private LinearLayout mLinBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_view);
        mPresenter = new RecordePresenter(this);
        inital();
    }

    @Override
    public void findView() {
        mRcy = ((RecyclerView) findViewById(R.id.recodes_rcy));
        mLinBlank = ((LinearLayout) findViewById(R.id.recode_blankLin));

        //dialog
        mCustomDialog = new CustomSelectDialog(this, true, R.layout.dialog_select);
    }

    @Override
    public void initView() {
        mRcy.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecordeAdapter();
        /*第一次加载数据库中的数据*/
        List<Recorde> recordes = DBManager.getInstance()
                .getSessionInstance()
                .getRecordeDao()
                .loadAll();

        if (recordes.size() == 0) {
            setBlank(true);
        }
        mAdapter.setData(recordes);
        mRcy.setAdapter(mAdapter);
        mRcy.addItemDecoration(new CustomItemDecoration());
        mRcy.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void bindListener() {

        mAdapter.setItemLongClickListener(this);
        mAdapter.setItemClickListener(this);
        mAdapter.setListBlankCallBack(this);
        mCustomDialog.setDialogListener(this);
    }

    public void onBack(View view) {
        finish();
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }

    @Override
    public void toPageWithData(Class clzz, TrackInfo info) {
        Intent intent = new Intent(this, clzz);
        intent.putExtra("trackInfo", info);
        intent.putExtra("record", recorde);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_in_a, R.anim.anim_out_a);
    }

    @Override
    public Recorde getRecords() {
        return recorde;
    }

    @Override
    public void setBlank(boolean isBlank) {
        if (isBlank) {
            mLinBlank.setVisibility(View.VISIBLE);
        } else {
            mLinBlank.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(Recorde recorde) {
        this.recorde = recorde;
        mPresenter.Search(recorde);
    }

    /**
     * 长按拨打电话
     *
     * @param recorde
     */
    @Override
    public void onItemLongClick(Recorde recorde) {
        this.recorde = recorde;
        mCustomDialog.show();
    }

    /**
     * dialog的回调 打电话
     */
    @Override
    public void onCallPhone() {
        mCustomDialog.dismiss();
        String comTel = recorde.getComTel();
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + comTel)));
    }

    /**
     * dialog的回调 删除记录
     */
    @Override
    public void onDeletRecord() {

        mCustomDialog.dismiss();
        Snackbar.make(mRcy, "确认删除?", Snackbar.LENGTH_LONG)
                .setAction("ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAdapter.deleItem(recorde);
                    }
                })
                .show();
    }

    @Override
    public void onInfoToBlank() {
        setBlank(true);
    }
}
