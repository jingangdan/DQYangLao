package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;

/**
 * 登录-忘记密码
 * Created by jingang on 2018/4/21.
 */

public class ForgetPwdActivity extends MyBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_res);
        setTvTitle("忘记密码");
        setIvBack();
    }
}
