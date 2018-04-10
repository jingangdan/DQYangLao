package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;

/**
 * 首页-左侧侧边栏-通用
 * Created by jingang on 2018/4/9.
 */

public class CurrencyActivity extends MyBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_currency);
        setTvTitle("通用");
        setIvBack();
    }
}
