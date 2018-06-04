package com.dq.yanglao.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.BaseRecyclerViewHolder;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.DeviceGet;
import com.dq.yanglao.bean.UserInfo2;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.CodeUtils;
import com.dq.yanglao.utils.DialogUtils;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ScreenManagerUtils;
import com.dq.yanglao.view.SwipeView;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by asus on 2018/4/27.
 */

public class DeviceActivity extends MyBaseActivity {
    @Bind(R.id.rvDeviceList)
    RecyclerView rvDeviceList;
    @Bind(R.id.tvDeviceAdd)
    TextView tvDeviceAdd;
    private List<DeviceGet.DataBean> deviceList = new ArrayList<>();
    private DeviceAdapter mAdapter;
    private String sign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_devicelist);
        ButterKnife.bind(this);
        setTvTitle("设备列表");
        setIvBack();
        getDevice();
    }

    @OnClick(R.id.tvDeviceAdd)
    public void onViewClicked() {
        Intent intent = new Intent(this, AddDeviceActivity.class);
        startActivityForResult(intent, CodeUtils.DEVICE_LIST);
    }

    public void getDevice() {
        mAdapter = new DeviceAdapter(this, deviceList);
        rvDeviceList.setLayoutManager(new LinearLayoutManager(this));
        rvDeviceList.setAdapter(mAdapter);

        String PATH_RSA = "uid=" + SPUtils.getPreference(this, "uid") + "&token=" + SPUtils.getPreference(this, "token");
        System.out.println("device = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            sign = Base64Utils.encode(encryptByte).toString();
            xUtilsGetDevice(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备列表
     *
     * @param sign
     */
    public void xUtilsGetDevice(String sign) {
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_GETDEVICE,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("设备列表 = " + result);
                        DeviceGet deviceGet = GsonUtil.gsonIntance().gsonToBean(result, DeviceGet.class);
                        if (deviceGet.getStatus() == 1) {
                            if (deviceGet.getData().size() > 0) {
                                deviceList.clear();
                                deviceList.addAll(deviceGet.getData());
                                mAdapter.notifyDataSetChanged();
                            } else {
                                deviceList.clear();
                                mAdapter.notifyDataSetChanged();
                                SPUtils.savePreference(DeviceActivity.this, "isBind", "0");//0 未绑定  1已绑定
                                SPUtils.savePreference(DeviceActivity.this, "deviceid", "");//记录deviceid
                                goToActivity(NoLoginActivity.class);
                                ScreenManagerUtils.getInstance().clearActivityStack();
                            }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.DEVICE_LIST) {
            if (resultCode == CodeUtils.DEVICE_ADD) {
                //
                if (!TextUtils.isEmpty(sign)) {
                    xUtilsGetDevice(sign);
                }
            }
        }
    }

    public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

        private Context mContext;
        private List<DeviceGet.DataBean> dataList;

        public DeviceAdapter(Context mContext, List<DeviceGet.DataBean> dataList) {
            this.mContext = mContext;
            this.dataList = dataList;
        }

        @Override
        public DeviceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_device_list, parent, false);
            return new DeviceAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DeviceAdapter.ViewHolder holder, final int position) {
//            if(dataList.get(position).getDevice().getDevice())
            holder.tvName.setText(dataList.get(position).getRelation());
            holder.tvCode.setText("设备号：" + dataList.get(position).getDevice().getDevice());

            if (SPUtils.getPreference(mContext, "deviceid").equals(dataList.get(position).getDevice().getId())) {
                dataList.get(position).setDefault(true);
            } else {
                dataList.get(position).setDefault(false);
            }

            if (dataList.get(position).isDefault()) {
                holder.butDefalt.setText("已为默认");
            } else {
                holder.butDefalt.setText("设为默认");
            }

            holder.butDefalt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDefalt();
                    dataList.get(position).setDefault(true);
                    SPUtils.savePreference(mContext, "deviceid", dataList.get(position).getDevice().getId());//记录deviceid
                    SwipeView.closeMenu(v);
                }
            });
            holder.butDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.showDialog(mContext, "提示", "确定要移除设备吗？", new DialogUtils.OnDialogListener() {
                        @Override
                        public void confirm() {
                            String PATH_RSA = "device_id=" + dataList.get(position).getDevice().getId()
                                    + "&uid=" + SPUtils.getPreference(mContext, "uid")
                                    + "&token=" + SPUtils.getPreference(mContext, "token");
                            try {
                                PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                                byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                                xUtilsUnbind(Base64Utils.encode(encryptByte).toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class ViewHolder extends BaseRecyclerViewHolder {

            private ImageView img;
            private TextView tvName, tvCode;
            private Button butDefalt, butDel;

            public ViewHolder(View view) {
                super(view);
                img = (ImageView) view.findViewById(R.id.itemIvDevice);
                tvName = (TextView) view.findViewById(R.id.itemTvDevice);
                tvCode = (TextView) view.findViewById(R.id.itemTvCode);
                butDefalt = (Button) view.findViewById(R.id.itemButDeviceDefalt);
                butDel = (Button) view.findViewById(R.id.itemButDeviceDel);

            }
        }
    }

    /*将所有默认状态设置为false*/
    public void setDefalt() {
        for (int i = 0; i < deviceList.size(); i++) {
            deviceList.get(i).setDefault(false);
        }
    }


    /**
     * 解绑设备
     *
     * @param signs
     */
    public void xUtilsUnbind(final String signs) {
        Map<String, Object> map = new HashMap<>();
        map.put("sign", signs);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_UNBIND,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("解绑设备 = " + result);
                        UserInfo2 u2 = GsonUtil.gsonIntance().gsonToBean(result, UserInfo2.class);
                        if (u2.getStatus() == 1) {
                            showMessage(u2.getData());
                            xUtilsGetDevice(sign);
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

}
