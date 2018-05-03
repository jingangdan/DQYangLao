package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.utils.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-短信提醒设置
 * Created by jingang on 2018/5/3.
 */

public class ACTActivity extends MyBaseActivity {


    @Bind(R.id.switch1)
    Switch switch1;
    @Bind(R.id.linActSos)
    LinearLayout linActSos;
    @Bind(R.id.switch2)
    Switch switch2;
    @Bind(R.id.linActSms)
    LinearLayout linActSms;
    @Bind(R.id.switch3)
    Switch switch3;
    @Bind(R.id.linActRemove)
    LinearLayout linActRemove;
    @Bind(R.id.switch4)
    Switch switch4;
    @Bind(R.id.linActRemoveSms)
    LinearLayout linActRemoveSms;
    @Bind(R.id.switch5)
    Switch switch5;
    @Bind(R.id.linActPedo)
    LinearLayout linActPedo;

    private String act;
    private boolean isSms, isLowbat, isRemove, idRemoveSms, isPedo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_act);
        ButterKnife.bind(this);
        setTvTitle("短信提醒");
        setIvBack();
    }

//    @OnClick({R.id.switch1, R.id.linActSos, R.id.linActSms, R.id.linActRemove, R.id.linActRemoveSms, R.id.linActPedo, R.id.butAct})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.switch1:
//                Switch sw = (Switch) view;
//                boolean isChecked = sw.isChecked();
//                if (isChecked) {
//                    showMessage("开启'");
//                } else {
//                    showMessage("关闭");
//                }
//                break;
//            case R.id.linActSos:
//                act = "SOSSMS";
//                break;
//            case R.id.linActSms:
//                ivActSms.setVisibility(View.VISIBLE);
//                act = "LOWBAT";
//                break;
//            case R.id.linActRemove:
//                ivActRemove.setVisibility(View.VISIBLE);
//                act = "REMOVE";
//                break;
//            case R.id.linActRemoveSms:
//                ivActRemoveSms.setVisibility(View.VISIBLE);
//                act = "REMOVESMS";
//                break;
//            case R.id.linActPedo:
//                ivActPedo.setVisibility(View.VISIBLE);
//                act = "PEDO";
//                break;
//            case R.id.butAct:
//                //[DQHB*用户id*LEN*(act),device_id,status]
//                MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*" + act + "," + SPUtils.getPreference(this, "deviceid") + "," + "1]");
//                break;
//        }
//    }

    @OnClick({R.id.switch1, R.id.linActSos, R.id.switch2, R.id.linActSms, R.id.switch3, R.id.linActRemove, R.id.switch4, R.id.linActRemoveSms, R.id.switch5, R.id.linActPedo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.switch1:
                break;
            case R.id.linActSos:
                break;
            case R.id.switch2:
                break;
            case R.id.linActSms:
                break;
            case R.id.switch3:
                break;
            case R.id.linActRemove:
                break;
            case R.id.switch4:
                break;
            case R.id.linActRemoveSms:
                break;
            case R.id.switch5:
                break;
            case R.id.linActPedo:
                break;
        }
    }
}
