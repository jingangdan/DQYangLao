package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.DeviceSetting;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.ForceExitReceiver;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-短信提醒设置
 * Created by jingang on 2018/5/3.
 */

public class ACTActivity extends MyBaseActivity implements OnCallBackTCP {
    @Bind(R.id.switch1)
    Switch switch1;
    @Bind(R.id.switch2)
    Switch switch2;
    @Bind(R.id.switch3)
    Switch switch3;
    @Bind(R.id.switch4)
    Switch switch4;
    @Bind(R.id.switch5)
    Switch switch5;

    private String act;
    private boolean isSms, isLowbat, isRemove, isRemoveSms, isPedo;

    private String uid, deviceid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_act);
        ButterKnife.bind(this);
        setTvTitle("短信提醒");
        setIvBack();

        uid = SPUtils.getPreference(this, "uid");
        deviceid = SPUtils.getPreference(this, "deviceid");

        getDeviceSetting();

        ForceExitReceiver.setOnClickListenerSOS(this);
    }

    @OnClick({R.id.switch1, R.id.switch2, R.id.switch3, R.id.switch4, R.id.switch5})
    public void onViewClicked(View view) {
        Switch sw = (Switch) view;
        //[DQHB*用户id*LEN*(act),device_id,status]
        switch (view.getId()) {
            case R.id.switch1:
                //[DQHB*用户id*LEN*(act),device_id,status]
                isSms = sw.isChecked();
                act = "SOSSMS";
                if (isSms) {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",1]");
                } else {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",0]");
                }
                break;
            case R.id.switch2:
                isLowbat = sw.isChecked();
                act = "LOWBAT";
                if (isLowbat) {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",1]");
                } else {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",1]");
                }
                break;
            case R.id.switch3:
                isRemove = sw.isChecked();
                act = "REMOVE";
                if (isRemove) {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",1]");
                } else {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",1]");
                }
                break;
            case R.id.switch4:
                isRemoveSms = sw.isChecked();
                act = "REMOVESMS";
                if (isRemoveSms) {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",1]");
                } else {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",1]");
                }
                break;
            case R.id.switch5:
                isPedo = sw.isChecked();
                act = "PEDO";
                if (isPedo) {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",1]");
                } else {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*" + act + "," + deviceid + ",1]");
                }
                break;
        }
    }

    /*获取设备设置*/
    public void getDeviceSetting() {
        String PATH_RSA = "device_id=" + SPUtils.getPreference(this, "deviceid") + "&uid=" + SPUtils.getPreference(this, "uid") + "&token=" + SPUtils.getPreference(this, "token");
        System.out.println("sos = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            xUtilsGetDeviceSetting(Base64Utils.encode(encryptByte).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备设置
     *
     * @param sign
     */
    public void xUtilsGetDeviceSetting(String sign) {
        System.out.println("设备设置 = " + HttpPath.DEVICE_SETTING + "sign=" + sign);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_SETTING,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("设备设置 = " + result);
                        DeviceSetting setting = GsonUtil.gsonIntance().gsonToBean(result, DeviceSetting.class);
                        if (setting.getStatus() == 1) {
                            setting.getData().get(0).getAct();
                            if (setting.getData().get(0).getVal().equals("1")) {
                                switch1.setChecked(true);
                            } else {
                                switch1.setChecked(false);
                            }
                            if (setting.getData().get(1).getVal().equals("1")) {
                                switch2.setChecked(true);
                            } else {
                                switch2.setChecked(false);
                            }
                            if (setting.getData().get(2).getVal().equals("1")) {
                                switch3.setChecked(true);
                            } else {
                                switch3.setChecked(false);
                            }
                            if (setting.getData().get(3).getVal().equals("1")) {
                                switch4.setChecked(true);
                            } else {
                                switch4.setChecked(false);
                            }
                            if (setting.getData().get(4).getVal().equals("1")) {
                                switch5.setChecked(true);
                            } else {
                                switch5.setChecked(false);
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    @Override
    public void onCallback(String type, String msg) {
        System.out.println("type = " + type + "   msg = " + msg);
        if (!type.equals("UL")) {
            showMessage("设置成功");
        }
    }
}
