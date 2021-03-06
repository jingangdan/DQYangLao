package com.dq.yanglao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.DeviceBind;
import com.dq.yanglao.bean.UserInfo2;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.CodeUtils;
import com.dq.yanglao.utils.ForceExitReceiver;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ScreenManagerUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的-设备列表-添加设备
 * Created by jingang on 2018/4/27.
 */

public class AddDeviceActivity extends MyBaseActivity implements OnCallBackTCP {
    @Bind(R.id.editAddDeviceCode)
    EditText editAddDeviceCode;
    @Bind(R.id.editAddDeviceName)
    EditText editAddDeviceName;
    @Bind(R.id.editAddDeviceRelation)
    EditText editAddDeviceRelation;
    @Bind(R.id.butAddDevice)
    Button butAddDevice;

    private boolean isClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_adddevice);
        ButterKnife.bind(this);
        setTvTitle("填写设备信息");
        setIvBack();

        ForceExitReceiver.setOnClickListenerSOS(this);
    }

    @OnClick(R.id.butAddDevice)
    public void onViewClicked() {
        if (TextUtils.isEmpty(editAddDeviceCode.getText().toString().trim())) {
            showMessage("设备编号为空");
            return;
        }
        if (TextUtils.isEmpty(editAddDeviceName.getText().toString().trim())) {
            showMessage("设备名称为空");
            return;
        }
        if (TextUtils.isEmpty(editAddDeviceRelation.getText().toString().trim())) {
            showMessage("与设备关系为空");
            return;
        }
        if (!isClick) {
            isClick = true;
            //绑定
            String PATH_RSA = "device=" + editAddDeviceCode.getText().toString().trim()
                    + "&nickname=" + editAddDeviceName.getText().toString().trim()
                    + "&relation=" + editAddDeviceRelation.getText().toString().trim()
                    + "&uid=" + SPUtils.getPreference(this, "uid")
                    + "&token=" + SPUtils.getPreference(this, "token");
            System.out.println("adde = " + PATH_RSA);

            try {
                PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                xUtilsBind(Base64Utils.encode(encryptByte).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 绑定设备
     *
     * @param afterencrypt
     */
    public void xUtilsBind(String afterencrypt) {
        System.out.println("绑定设备 = " + HttpPath.DEVICE_BIND + "sign=" + afterencrypt);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", afterencrypt);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_BIND,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("绑定设备 = " + result);
                        isClick = false;
                        try {
                            JSONObject json = new JSONObject(result);
                            if (json.getInt("status") == 1) {
                                final DeviceBind device = GsonUtil.gsonIntance().gsonToBean(result, DeviceBind.class);
                                if (device.getData().getIs_primary() == 1) {
                                    //是主账号 绑定成功
                                    SPUtils.savePreference(AddDeviceActivity.this, "isBind", "1");//0 未绑定  1已绑定
                                    SPUtils.savePreference(AddDeviceActivity.this, "deviceid", device.getData().getDevice_id());//记录deviceid

                                    goToActivity(MainActivity.class);
                                    ScreenManagerUtils.getInstance().removeActivity(AddDeviceActivity.this);
                                }
                                if (device.getData().getIs_primary() == 0) {
                                    MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(AddDeviceActivity.this, "uid") + "*16" + "*APPLY," + device.getData().getId() + "]");
                                    showMessage("发送成功，等待审核");
                                }
                            } else if (json.getInt("status") == 0) {
                                showMessage(json.getString("data"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        final DeviceBind device = GsonUtil.gsonIntance().gsonToBean(result, DeviceBind.class);
//                        if (device.getStatus() == 1) {
//                            if (device.getData().getIs_primary() == 1) {
//                                //是主账号 绑定成功
//                                SPUtils.savePreference(AddDeviceActivity.this, "isBind", "1");//0 未绑定  1已绑定
//                                SPUtils.savePreference(AddDeviceActivity.this, "deviceid", device.getData().getDevice_id());//记录deviceid
//
//                                goToActivity(MainActivity.class);
//                                ScreenManagerUtils.getInstance().removeActivity(AddDeviceActivity.this);
//
//                            }
//                            if (device.getData().getIs_primary() == 0) {
////                                MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(AddDeviceActivity.this, "uid") + "*16" + "*APPLY," + device.getData().getId() + "]");
//                                MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(AddDeviceActivity.this, "uid") + "*16" + "*APPLY," + device.getData().getId() + "]");
//                                showMessage("发送成功，等待审核");
//                                //AddDeviceActivity.this.finish();
//                            }
//                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        isClick = false;
//                        if (!TextUtils.isEmpty(string)) {
//                            UserInfo2 u2 = new UserInfo2();
//                            if (u2.getStatus() == 0) {
//                                showMessage(u2.getData());
//                            }
//                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        isClick = false;
                    }

                    @Override
                    public void onFinished() {
                        isClick = false;
                    }
                });
    }

    @Override
    public void onCallback(String type, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            String[] temp = null;
            temp = msg.split(",");//以逗号拆分
            if (temp[1].equals("1")) {
                //同意
                showMessage("添加成功");
                Intent intent = new Intent();
                setResult(CodeUtils.DEVICE_ADD, intent);
                this.finish();
            }
            if (temp[1].equals("0")) {
                //拒绝
                showMessage("请求被拒绝");
            }
        }

    }
}
