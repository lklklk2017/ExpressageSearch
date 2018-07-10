package com.cdxxgc.expressagesearchdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.entity.Company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */
public class TelAdapter extends RecyclerView.Adapter<TelAdapter.TelViewHolder> {

    private List<Company> list = new ArrayList<>();
    private ItemClickListener listener;

    public TelAdapter(List<Company> list) {
        this.list = list;
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public TelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tel, parent, false);
        return new TelViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(TelViewHolder holder, int position) {

        Company company = list.get(position);
        holder.mImg.setImageResource(company.getRes_imgId());
        holder.mTvComName.setText(company.getName());
        holder.mTvComTel.setText(company.getTel());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class TelViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImg;
        public TextView mTvComName;
        public TextView mTvComTel;

        public TelViewHolder(View itemView, final ItemClickListener listener) {
            super(itemView);
            mImg = ((ImageView) itemView.findViewById(R.id.tel_item_portrait_IMG));
            mTvComName = ((TextView) itemView.findViewById(R.id.tel_item_compName_TV));
            mTvComTel = ((TextView) itemView.findViewById(R.id.tel_item_tel_TV));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
