package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册
 * Created by jingang on 2018/4/9.
 */

public class ResActivity extends MyBaseActivity {
    @Bind(R.id.etResEqp)
    EditText etResEqp;
    @Bind(R.id.etResAct)
    EditText etResAct;
    @Bind(R.id.tvResNext)
    TextView tvResNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_res);
        setTvTitle("添加设备");
        setIvBack();

    }

    @OnClick(R.id.tvResNext)
    public void onViewClicked() {
    }
}
