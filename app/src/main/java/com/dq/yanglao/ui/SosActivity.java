package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.utils.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置亲情号码
 * Created by jingang on 2018/4/26.
 */

public class SosActivity extends MyBaseActivity {
    @Bind(R.id.editSos1)
    EditText editSos1;
    @Bind(R.id.editSos2)
    EditText editSos2;
    @Bind(R.id.editSos3)
    EditText editSos3;
    @Bind(R.id.butSos)
    Button butSos;

    private String phone1, phone2, phone3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_sos);
        ButterKnife.bind(this);
        setTvTitle("亲亲号码");
        setIvBack();
    }

    @OnClick(R.id.butSos)
    public void onViewClicked() {
        phone1 = editSos1.getText().toString().trim();
        phone2 = editSos2.getText().toString().trim();
        phone3 = editSos3.getText().toString().trim();

        if (!TextUtils.isEmpty(phone1)) {
            //[DQHB*uid*LEN*SOS1,device_id,号码]
            MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*SOS1," + SPUtils.getPreference(this, "deviceid") + "," + phone1 + "]");
        }

    }
}
