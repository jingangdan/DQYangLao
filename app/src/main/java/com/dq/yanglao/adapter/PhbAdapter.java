package com.dq.yanglao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.BaseRecyclerViewHolder;
import com.dq.yanglao.bean.PhbList;

import java.util.List;

/**
 * Created by asus on 2018/4/27.
 */

public class PhbAdapter extends RecyclerView.Adapter<PhbAdapter.MyViewHolder> {
    private Context mContext;
    private List<PhbList.DataBean> dataList;

    public PhbAdapter(Context mContext, List<PhbList.DataBean> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_phb, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.mobile.setText(dataList.get(position).getMobile());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends BaseRecyclerViewHolder {
        TextView name, mobile;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.itemTvPhbName);
            mobile = (TextView) view.findViewById(R.id.itemTvPhbMobile);
        }
    }
}
