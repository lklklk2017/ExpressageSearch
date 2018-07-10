package com.cdxxgc.expressagesearchdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.entity.Recorde;
import com.cdxxgc.expressagesearchdemo.utils.DBManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/2.
 */

public class RecordeAdapter extends RecyclerView.Adapter<RecordeAdapter.RecordeViewHolder> {

    private List<Recorde> list = new ArrayList<Recorde>();
    private RecodesClickLisener listener;
    private RecodesLongClickLisener longlistener;
    private OnBlankCallBack blankCallBack;
    private SimpleDateFormat mSdf;
    private SimpleDateFormat mSdf_des;
    private int index;

    public RecordeAdapter() {
        mSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mSdf_des = new SimpleDateFormat("MM-dd");

    }

    /**
     * 获取数据源（进行一次倒叙排列：将后加的数据首先显示）
     *
     * @param list
     */
    public void setData(List<Recorde> list) {
        Collections.reverse(list);
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 删除数据源中的数据
     * 通知移除指定的item
     *
     * @param recorde
     */
    public void deleItem(Recorde recorde) {
        DBManager.getInstance().getSessionInstance().getRecordeDao().delete(recorde);
        list.remove(recorde);
        int size = list.size();
        if (size == 0) {
            if (blankCallBack != null) {
                blankCallBack.onInfoToBlank();
            }
        }
        notifyItemRemoved(index);
    }

    public void setItemClickListener(RecodesClickLisener listener) {
        this.listener = listener;
    }

    public void setItemLongClickListener(RecodesLongClickLisener listener) {
        this.longlistener = listener;
    }

    public void setListBlankCallBack(OnBlankCallBack blankCallBack) {
        this.blankCallBack = blankCallBack;
    }

    @Override
    public RecordeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recordes, parent, false);
        return new RecordeViewHolder(view, listener, longlistener);
    }

    @Override
    public void onBindViewHolder(RecordeViewHolder holder, int position) {
        Recorde recorde = list.get(position);

        holder.mImg.setImageResource(recorde.getRes_imgId());
        holder.mTvCompName.setText(recorde.getCompName());
        holder.mTvCompCode.setText("" + recorde.getLogisticCode());
        holder.mTvState.setText(recorde.getLastAcceptStation());
        try {
            Date date = mSdf.parse(recorde.getLastAcceptTime());
            String time = mSdf_des.format(date);
            holder.mTvTime.setText(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class RecordeViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImg;
        public TextView mTvCompName;
        public TextView mTvCompCode;
        public TextView mTvState;
        public TextView mTvTime;

        public RecordeViewHolder(View itemView, final RecodesClickLisener listener, final RecodesLongClickLisener longListener) {
            super(itemView);
            mImg = ((ImageView) itemView.findViewById(R.id.records_item_portrait_IMG));
            mTvCompName = ((TextView) itemView.findViewById(R.id.records_item_compName_TV));
            mTvCompCode = ((TextView) itemView.findViewById(R.id.records_item_compCode_TV));
            mTvState = ((TextView) itemView.findViewById(R.id.records_item_State_TV));
            mTvTime = ((TextView) itemView.findViewById(R.id.records_item_time_TV));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(list.get(getAdapterPosition()));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (longlistener != null) {
                        index = getAdapterPosition();
                        longListener.onItemLongClick(list.get(index));
                    }
                    return true;
                }
            });
        }

    }

    public interface RecodesClickLisener {
        void onItemClick(Recorde recorde);
    }

    public interface RecodesLongClickLisener {
        void onItemLongClick(Recorde recorde);
    }

    public interface OnBlankCallBack {
        void onInfoToBlank();
    }
}
