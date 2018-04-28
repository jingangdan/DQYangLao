package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by asus on 2018/4/27.
 */

public class TestActivity extends MyBaseActivity {
    @Bind(R.id.editTest)
    EditText editTest;
    @Bind(R.id.butTest)
    Button butTest;
    @Bind(R.id.tvTest)
    TextView tvTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.butTest)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(editTest.getText().toString().trim())) {
            MyApplacation.tcpClient.send(URLEncoder.encode(editTest.getText().toString().trim()));

            tvTest.setText(URLEncoder.encode(editTest.getText().toString().trim()));

        } else {
            showMessage("输入为空");
        }
    }
}
