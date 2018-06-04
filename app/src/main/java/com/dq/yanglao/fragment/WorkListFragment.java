package com.dq.yanglao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dq.yanglao.Interface.OnItemClickListener;
import com.dq.yanglao.R;
import com.dq.yanglao.base.BaseRecyclerViewHolder;
import com.dq.yanglao.base.ListBean;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.utils.TimeUtils;
import com.dq.yanglao.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 工单
 * Created by jingang on 2018/5/14.
 */

public class WorkListFragment extends MyBaseFragment {
    @Bind(R.id.rvWorkList)
    RecyclerView rvWorkList;
    @Bind(R.id.tvWorkListTop)
    TextView tvWorkListTop;
    private Adapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_work_list, null);
        ButterKnife.bind(this, view);
        setTopMargin();
        initDate();
        return view;
    }

    public void setTopMargin() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(tvWorkListTop.getLayoutParams());
        lp.setMargins(0, TimeUtils.getStatusBarHeight(getActivity()), 0, 0);
        tvWorkListTop.setLayoutParams(lp);
    }

    public void initDate() {
        mAdapter = new Adapter(new ArrayList<ListBean>() {{
            add(new ListBean(R.mipmap.ic_launcher, "找服务"));
            add(new ListBean(R.mipmap.ic_launcher, "发服务"));
            add(new ListBean(R.mipmap.ic_launcher, "查服务"));
            add(new ListBean(R.mipmap.ic_launcher, "服务统计"));
        }}, getActivity());
        rvWorkList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvWorkList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.getInstance(getActivity()).showMessage("" + position);
            }
        });
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
        private Context mContext;
        private List<ListBean> list;
        private OnItemClickListener onItemClickListener;

        public Adapter(List<ListBean> list, Context mContext) {
            this.list = list;
            this.mContext = mContext;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false));
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
            holder.img.setImageResource(list.get(position).getImg());
            holder.title.setText(list.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends BaseRecyclerViewHolder {
            private ImageView img;
            private TextView title;

            public MyViewHolder(View view) {
                super(view);
                img = (ImageView) view.findViewById(R.id.item_list_img);
                title = (TextView) view.findViewById(R.id.item_list_title);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
