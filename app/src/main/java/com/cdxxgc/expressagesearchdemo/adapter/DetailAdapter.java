package com.cdxxgc.expressagesearchdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdxxgc.expressagesearchdemo.DetailPage.DetailView;
import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.entity.TrackInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/23.
 * 详情页面适配器
 */

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TrackInfo.TracesBean> list = new ArrayList<TrackInfo.TracesBean>();
    private SimpleDateFormat mSdf;
    private SimpleDateFormat mSdf_date;
    private SimpleDateFormat mSdf_time;
    private DetailHViewHolder holder;

    public DetailAdapter() {
        //2018-03-13 00:27:23
        mSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mSdf_date = new SimpleDateFormat("yyyy.MM.dd");
        mSdf_time = new SimpleDateFormat("HH:mm");
    }

    public void setData(List<TrackInfo.TracesBean> traces) {
        this.list = traces;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {//头布局
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_head, parent, false);
            return new DetailHViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);
            return new DetailViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TrackInfo.TracesBean tracesBean = list.get(list.size() - position - 1);
        String acceptTime = tracesBean.getAcceptTime();//时间
        String acceptStation = tracesBean.getAcceptStation();//内容

        if (holder instanceof DetailHViewHolder) {
            DetailHViewHolder holder_head = (DetailHViewHolder) holder;

            try {
                Date date = mSdf.parse(acceptTime);
                String dateForm = mSdf_date.format(date.getTime());
                String timeForm = mSdf_time.format(date.getTime());

                holder_head.mTvDate.setText(dateForm);
                holder_head.mTvTime.setText(timeForm);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder_head.mTvContent.setText(acceptStation);

        } else if (holder instanceof DetailViewHolder) {
            DetailViewHolder holder_other = (DetailViewHolder) holder;

            try {
                Date date = mSdf.parse(acceptTime);
                String dateForm = mSdf_date.format(date.getTime());
                String timeForm = mSdf_time.format(date.getTime());

                holder_other.mTvDate.setText(dateForm);
                holder_other.mTvTime.setText(timeForm);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder_other.mTvContent.setText(acceptStation);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvDate;
        private TextView mTvTime;
        private TextView mTvContent;
        private ImageView mImg;

        public DetailViewHolder(View itemView) {
            super(itemView);
            mTvDate = ((TextView) itemView.findViewById(R.id.detail_item_date_TV));
            mTvTime = ((TextView) itemView.findViewById(R.id.detail_item_time_TV));
            mTvContent = ((TextView) itemView.findViewById(R.id.detail_item_content_TV));
            mImg = ((ImageView) itemView.findViewById(R.id.detail_item_IMG));
        }
    }

    static class DetailHViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvDate;
        private TextView mTvTime;
        private TextView mTvContent;
        private ImageView mImg;

        public DetailHViewHolder(View itemView) {
            super(itemView);
            mTvDate = ((TextView) itemView.findViewById(R.id.detail_item_head_date_TV));
            mTvTime = ((TextView) itemView.findViewById(R.id.detail_item_head_time_TV));
            mTvContent = ((TextView) itemView.findViewById(R.id.detail_item_head_content_TV));
            mImg = ((ImageView) itemView.findViewById(R.id.detail_item_head_IMG));
        }
    }
}
