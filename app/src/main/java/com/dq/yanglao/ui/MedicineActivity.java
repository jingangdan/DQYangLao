package com.dq.yanglao.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dq.yanglao.Interface.OnItemClickListener;
import com.dq.yanglao.R;
import com.dq.yanglao.base.BaseRecyclerViewHolder;
import com.dq.yanglao.base.MyBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页-吃药提醒
 * Created by jingang on 2018/6/4.
 */

public class MedicineActivity extends MyBaseActivity {
    @Bind(R.id.rvMedicine)
    RecyclerView rvMedicine;
    private MedicineListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_medicine);
        ButterKnife.bind(this);
        setTvTitle("吃药提醒");
        setIvBack();
        getIvRight().setImageResource(R.mipmap.ic_medicine_add);
        getIvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicineActivity.this, MedicineUpdateActivity.class));
            }
        });
        setAdapter();
    }

    public void setAdapter() {
        mAdapter = new MedicineListAdapter(this);
        rvMedicine.setLayoutManager(new LinearLayoutManager(this));
        //rvMedicine.setAdapter(new MedicineListAdapter(this));
        rvMedicine.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(MedicineActivity.this, MedicineUpdateActivity.class));
            }
        });

    }

    public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MyViewHolder> {
        private Context mContext;
        private OnItemClickListener onItemClickListener;

        public MedicineListAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_medicine_list, parent, false));
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

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class MyViewHolder extends BaseRecyclerViewHolder {

            public MyViewHolder(View view) {
                super(view);
            }
        }
    }

}
