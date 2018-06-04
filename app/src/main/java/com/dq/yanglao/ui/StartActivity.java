package com.dq.yanglao.ui;

import android.content.Context;
import android.os.Bundle;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.StatusBarUtils;

/**
 * 启动页
 * Created by jingang on 2016/10/17.
 */
public class StartActivity extends MyBaseActivity {
    public static Context context;
    private String isLogin, isBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setBaseContentView(R.layout.activity_start);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //获取本地轻量型存储的是否登录、是否绑定设备标记
                isLogin = SPUtils.getPreference(StartActivity.this, "isLogin");
                isBind = SPUtils.getPreference(StartActivity.this, "isBind");

                if (isLogin.equals("") || isLogin.equals("0")) {
                    goToActivity(LoginActivity.class);
                }
                if (isLogin.equals("1")) {
                    if (isBind.equals("") || isBind.equals("0")) {
                        goToActivity(NoLoginActivity.class);
                    }
                    if (isBind.equals("1")) {
                        goToActivity(MainActivity.class);
                    }
                }
                finish();
            }
        }).start();
    }

}