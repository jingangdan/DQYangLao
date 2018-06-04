package com.dq.yanglao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.utils.DialogUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ScreenManagerUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-设置
 * Created by jingang on 2018/6/1.
 */

public class SettingActivity extends MyBaseActivity {
    @Bind(R.id.tvSettingName)
    TextView tvSettingName;
    @Bind(R.id.tvSettingMobile)
    TextView tvSettingMobile;
    @Bind(R.id.butSettingOut)
    Button butSettingOut;

    private String name, mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setTvTitle("设置");
        setIvBack();
//        intent = getIntent();
//        name = intent.getStringExtra("name");
//        mobile = intent.getStringExtra("mobile");

        name = getIntent().getStringExtra("name");
        mobile = getIntent().getStringExtra("mobile");
        if (TextUtils.isEmpty(name)) {
            tvSettingName.setText("用户" + mobile);
        } else {
            tvSettingName.setText(name);
        }
        tvSettingMobile.setText(mobile);
    }

    @OnClick(R.id.butSettingOut)
    public void onViewClicked() {
        DialogUtils.showDialog(this, "提示：", "确定要退出登录吗？", new DialogUtils.OnDialogListener() {
            @Override
            public void confirm() {
                SPUtils.savePreference(SettingActivity.this, "isLogin", "0");//0 未登录  1已登录
                SPUtils.savePreference(SettingActivity.this, "isBind", "0");

                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                //ScreenManagerUtils.getInstance().removeActivity(SettingActivity.this);
                ScreenManagerUtils.getInstance().clearActivityStack();

//             MyApplacation.tcpClient.closeSelf();
                MyApplacation.tcpHelper.closeTCP();

            }

            @Override
            public void cancel() {

            }
        });
    }
}
