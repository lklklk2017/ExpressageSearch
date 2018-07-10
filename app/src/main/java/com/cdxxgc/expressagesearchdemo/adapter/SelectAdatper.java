package com.cdxxgc.expressagesearchdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdxxgc.expressagesearchdemo.R;
import com.cdxxgc.expressagesearchdemo.entity.Company;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/22.
 * 选择公司对应的适配器
 */

public class SelectAdatper extends RecyclerView.Adapter<SelectAdatper.SelectViewHolder> {

    private static OnItemClickListener listener;
    private Context context;
    private ArrayList<Company> list = new ArrayList<Company>();

    public SelectAdatper(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Company> mCompanyList) {
        if (mCompanyList != null) {
            this.list = mCompanyList;
            notifyDataSetChanged();
        }
    }

    public void setOnItemListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public SelectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_item, parent, false);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SelectViewHolder holder, int position) {

        Company company = list.get(position);

        holder.mTvComName.setText(company.getName());
        holder.mImgProt.setImageResource(company.getRes_imgId());
        holder.mTvTel.setText(company.getTel());
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }

    static class SelectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTvComName;
        public ImageView mImgProt;
        public TextView mTvTel;
        private RelativeLayout mRelItem;

        public SelectViewHolder(View itemView) {
            super(itemView);
            mTvComName = ((TextView) itemView.findViewById(R.id.select_item_compName_TV));
            mImgProt = ((ImageView) itemView.findViewById(R.id.select_item_portrait_IMG));
            mTvTel = ((TextView) itemView.findViewById(R.id.select_item_tel_TV));
            mRelItem = ((RelativeLayout) itemView.findViewById(R.id.select_item));
            mRelItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.select_item:
                    if (listener != null) {
                        listener.onItemClick(getLayoutPosition());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
