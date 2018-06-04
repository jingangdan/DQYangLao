package com.dq.yanglao.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dq.yanglao.R;
import com.dq.yanglao.base.BaseRecyclerViewHolder;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.Family;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.view.GlideCircleTransform;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的-家庭成员
 * Created by jingang on 2018/5/7.
 */

public class FamilyActivity extends MyBaseActivity {
    @Bind(R.id.rvFamily)
    RecyclerView rvFamily;
    private FamilyAdapter mAdapter;
    private List<Family.DataBean> familyList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_family);
        ButterKnife.bind(this);
        setTvTitle("家庭成员");
        setIvBack();

        getFamily();

    }

    public void getFamily() {

        mAdapter = new FamilyAdapter(this, familyList);
        rvFamily.setLayoutManager(new LinearLayoutManager(this));
        rvFamily.setAdapter(mAdapter);

        String PATH_RSA = "device_id=" + SPUtils.getPreference(this, "deviceid") + "&uid=" + SPUtils.getPreference(this, "uid") + "&token=" + SPUtils.getPreference(this, "token");
        System.out.println(" = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            xUtilsGerList(Base64Utils.encode(encryptByte).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xUtilsGerList(String sign) {
        System.out.println("家庭成员 = " + HttpPath.DEVICE_GETLIST + "sign=" + sign);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_GETLIST,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("家庭成员 = " + result);
                        Family family = GsonUtil.gsonIntance().gsonToBean(result, Family.class);
                        if (family.getStatus() == 1) {
                            familyList.clear();
                            familyList.addAll(family.getData());
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }


    public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.MyViewHolder> {
        private Context mContext;
        private List<Family.DataBean> dataList;

        public FamilyAdapter(Context mContext, List<Family.DataBean> dataList) {
            this.mContext = mContext;
            this.dataList = dataList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_family, parent, false));
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Glide.with(mContext)
                    .load(HttpPath.PATH + dataList.get(position).getHeadimg())
                    .bitmapTransform(new GlideCircleTransform(mContext))
                    .crossFade(1000)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.ivHeader);

            if (TextUtils.isEmpty(dataList.get(position).getName())) {
                holder.tvName.setText("用户" + dataList.get(position).getMobile());
            } else {
                holder.tvName.setText(dataList.get(position).getName());
            }

            holder.tvMobile.setText("账号：" + dataList.get(position).getMobile());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class MyViewHolder extends BaseRecyclerViewHolder {
            private ImageView ivHeader;
            private TextView tvName, tvMobile;

            public MyViewHolder(View view) {
                super(view);
                ivHeader = (ImageView) view.findViewById(R.id.ivItemFamily);
                tvName = (TextView) view.findViewById(R.id.tvItemFamilyName);
                tvMobile = (TextView) view.findViewById(R.id.tvItemFamilyMobile);
            }
        }
    }
}
