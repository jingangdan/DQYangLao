package com.dq.yanglao.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dq.yanglao.Interface.OnItemClickListener;
import com.dq.yanglao.R;
import com.dq.yanglao.base.BaseRecyclerViewHolder;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.Times;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页-吃药提醒-编辑吃药提醒-选择时间
 * Created by jingang on 2018/6/4.
 */

public class MedicineUpdateTimeActivity extends MyBaseActivity {
    @Bind(R.id.rvMedicineUpdateTime)
    RecyclerView rvMedicineUpdateTime;

    private Adapter mAdapter;
    private List<Times> timesList;
    private Times times;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_medicine_update_time);
        ButterKnife.bind(this);
        setTvTitle("选择时间");
        setIvBack();
        getTvRight().setText("存储");
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        initDate();
        setAdapter();
    }

    public void initDate() {
        timesList = new ArrayList<>();
        timesList.add(new Times("每周日",true));
        timesList.add(new Times("每周一",true));
        timesList.add(new Times("每周二",true));
        timesList.add(new Times("每周三",true));
        timesList.add(new Times("每周四",true));
        timesList.add(new Times("每周五",true));
        timesList.add(new Times("每周六",true));

    }

    public void setAdapter() {
        mAdapter = new Adapter(this,timesList);
        rvMedicineUpdateTime.setLayoutManager(new LinearLayoutManager(this));
        rvMedicineUpdateTime.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(timesList.get(position).isSelect() == false){

                }
            }
        });

    }

    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
        private Context mContext;
        private List<Times> timesList;
        private OnItemClickListener onItemClickListener;

        public Adapter(Context mContext, List<Times> timesList) {
            this.mContext = mContext;
            this.timesList = timesList;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_medicine_update_time, parent, false));
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
            holder.tv.setText(timesList.get(position).getName());
            if (timesList.get(position).isSelect() == false) {
                holder.iv.setVisibility(View.INVISIBLE);
            } else if (timesList.get(position).isSelect() == true) {
                holder.iv.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return timesList.size();
        }

        public class MyViewHolder extends BaseRecyclerViewHolder {
            private TextView tv;
            private ImageView iv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.itemMedicineUpdateTimeTv);
                iv = (ImageView) view.findViewById(R.id.itemMedicineUpdateTimeIv);
            }
        }
    }
}
