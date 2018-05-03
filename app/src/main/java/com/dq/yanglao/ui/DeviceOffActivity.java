package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 我的-远程关机
 * Created by jingang on 2018/5/3.
 */

public class DeviceOffActivity extends MyBaseActivity {
    @Bind(R.id.ivDeviceOff)
    ImageView ivDeviceOff;
    @Bind(R.id.tvDeviceOff)
    TextView tvDeviceOff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_deviceoff);
        setTvTitle("远程关机");
        setIvBack();
    }

    @OnClick(R.id.ivDeviceOff)
    public void onViewClicked() {
    }
}
