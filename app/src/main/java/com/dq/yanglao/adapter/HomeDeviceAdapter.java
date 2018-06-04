package com.dq.yanglao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dq.yanglao.Interface.OnItemClickListener;
import com.dq.yanglao.R;
import com.dq.yanglao.base.BaseRecyclerViewHolder;
import com.dq.yanglao.bean.DeviceGet;

import java.util.List;

/**
 * 首页-设备列表
 * Created by jingang on 2018/5/8.
 */

public class HomeDeviceAdapter extends RecyclerView.Adapter<HomeDeviceAdapter.MyViewHolder> {
    private Context mContext;
    private List<DeviceGet.DataBean> dataList;
    private OnItemClickListener onItemClickListener;

    public HomeDeviceAdapter(Context mContext, List<DeviceGet.DataBean> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_device, parent, false));
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
        holder.tvHomeDevice.setText(dataList.get(position).getDevice().getDevice());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends BaseRecyclerViewHolder {
        private TextView tvHomeDevice;

        public MyViewHolder(View view) {
            super(view);
            tvHomeDevice = (TextView) view.findViewById(R.id.tvHomeDevice);
        }
    }
}
