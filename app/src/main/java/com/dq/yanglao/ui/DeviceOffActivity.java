package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.utils.DialogUtils;
import com.dq.yanglao.utils.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-远程关机
 * Created by jingang on 2018/5/3.
 */

public class DeviceOffActivity extends MyBaseActivity implements OnCallBackTCP {
    @Bind(R.id.ivDeviceOff)
    ImageView ivDeviceOff;
    @Bind(R.id.tvDeviceOff)
    TextView tvDeviceOff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_deviceoff);
        ButterKnife.bind(this);
        setTvTitle("远程关机");
        setIvBack();
    }

    @OnClick(R.id.ivDeviceOff)
    public void onViewClicked() {
        DialogUtils.showDialog(this, "提示：", "您确定要将此设备关机？", new DialogUtils.OnDialogListener() {
            @Override
            public void confirm() {
                //[DQHB*uid*LEN*POWEROFF,设备id]
                MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(DeviceOffActivity.this, "uid") + "*16*POWEROFF," + SPUtils.getPreference(DeviceOffActivity.this, "deviceid") + "]");
            }

            @Override
            public void cancel() {

            }
        });
    }

    @Override
    public void onCallback(String type, String msg) {
        if (type.equals("POWEROFF")) {
            showMessage("请求成功");
        }
    }
}
