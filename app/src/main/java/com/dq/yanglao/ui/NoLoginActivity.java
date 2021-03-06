package com.dq.yanglao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.Interface.OnClickListeners;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.DeviceBind;
import com.dq.yanglao.bean.DeviceGet;
import com.dq.yanglao.bean.UserInfo2;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.DialogUtils;
import com.dq.yanglao.utils.ForceExitReceiver;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ScreenManagerUtils;
import com.dq.yanglao.view.rollpagerview.ImageLoopAdapter;
import com.dq.yanglao.view.rollpagerview.RollPagerView;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 未绑定设备
 * Created by jingang on 2018/4/24.
 */

public class NoLoginActivity extends MyBaseActivity implements OnCallBackTCP {
    private NoLoginActivity TAG = NoLoginActivity.this;

    @Bind(R.id.rpvNoLogin)
    RollPagerView rollPagerView;
    @Bind(R.id.butNoLoginOut)
    Button butNoLoginOut;
    @Bind(R.id.butNoLoginMap)
    Button butNoLoginMap;

    @Bind(R.id.butJoin)
    Button butJoin;
    @Bind(R.id.editJoin)
    EditText editJoin;

    private String PATH_RSA;
    private String bind_result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_nologin);
        ButterKnife.bind(this);
        setTvTitle("未绑定设备");
//        setIvBack();

        initDate();

        ForceExitReceiver.setOnClickListenerSOS(this);
    }

    public void initDate() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rollPagerView.getLayoutParams();
        params.height = width / 3;//宽高比 1:3
        rollPagerView.setLayoutParams(params);

        rollPagerView.setAdapter(new ImageLoopAdapter(rollPagerView, this));
        rollPagerView.setOnItemClickListener(new OnClickListeners() {
            @Override
            public void onItemClick(int position) {

            }
        });
    }

    @OnClick({R.id.butNoLoginOut, R.id.butNoLoginMap, R.id.butJoin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.butNoLoginOut:
                //退出登录
                DialogUtils.showDialog(TAG, "提示：", "确定要退出登录吗？", new DialogUtils.OnDialogListener() {
                    @Override
                    public void confirm() {
                        SPUtils.savePreference(TAG, "isLogin", "0");//0 未登录  1已登录
                        //SPUtils.savePreference(TAG, "isBind", "0");

                        startActivity(new Intent(TAG, LoginActivity.class));
                        ScreenManagerUtils.getInstance().removeActivity(TAG);

                        MyApplacation.tcpHelper.closeTCP();
                    }

                    @Override
                    public void cancel() {

                    }
                });
                break;

            case R.id.butNoLoginMap:
                DialogUtils.showDialog(TAG, "提示：", "您的账号尚未绑定任何设备，去绑定吧！", new DialogUtils.OnDialogListener() {
                    @Override
                    public void confirm() {
                        startActivity(new Intent(TAG, AddDeviceActivity.class));
                    }

                    @Override
                    public void cancel() {

                    }
                });
                break;

            case R.id.butJoin:
                if (!TextUtils.isEmpty(editJoin.getText().toString().trim())) {
                    PATH_RSA = "device=" + editJoin.getText().toString().trim()
                            + "&uid=" + SPUtils.getPreference(NoLoginActivity.this, "uid")
                            + "&token=" + SPUtils.getPreference(NoLoginActivity.this, "token");
                    try {
                        PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                        byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                        // bind(URLEncoder.encode(Base64Utils.encode(encryptByte).toString(), "UTF-8"));
                        xUtilsBind(Base64Utils.encode(encryptByte).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showMessage("请填写设备号");
                }
                break;
        }


    }

    @Override
    public void onCallback(String type, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            String[] temp = null;
            temp = msg.split(",");//以逗号拆分
            if (temp[1].equals("1")) {
                String PATH_RSA = "uid=" + SPUtils.getPreference(this, "uid") + "&token=" + SPUtils.getPreference(this, "token");
                try {
                    PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                    byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                    getDevice(Base64Utils.encode(encryptByte).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (temp[1].equals("0")) {
                showMessage("绑定失败");
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
                        bind_result = result;
                        System.out.println("绑定设备 = " + result);
                        final DeviceBind device = GsonUtil.gsonIntance().gsonToBean(result, DeviceBind.class);
                        if (device.getStatus() == 1) {
                            if (device.getData().getIs_primary() == 1) {
                                //是主账号 绑定成功 跳到主界面
                                SPUtils.savePreference(NoLoginActivity.this, "isBind", "1");//0 未绑定  1已绑定
                                SPUtils.savePreference(NoLoginActivity.this, "deviceid", device.getData().getDevice_id());//记录deviceid
                                goToActivity(MainActivity.class);
                                ScreenManagerUtils.getInstance().removeActivity(NoLoginActivity.this);
                                NoLoginActivity.this.finish();
                            }
                            if (device.getData().getIs_primary() == 0) {
//                                MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(NoLoginActivity.this, "uid") + "*16" + "*APPLY," + device.getData().getId() + "]");
                                MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(NoLoginActivity.this, "uid") + "*16" + "*APPLY," + device.getData().getId() + "]");
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if (!TextUtils.isEmpty(bind_result)) {
                            UserInfo2 u2 = GsonUtil.gsonIntance().gsonToBean(bind_result, UserInfo2.class);
                            if (u2.getStatus() == 0) {
                                showMessage(u2.getData());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    /**
     * 获取用户绑定设备
     *
     * @param afterencrypt
     */
    public void getDevice(String afterencrypt) {
        System.out.println("用户绑定设备 = " + HttpPath.DEVICE_GETDEVICE + "sign=" + afterencrypt);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", afterencrypt);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_GETDEVICE,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("用户绑定设备 = " + result);
                        DeviceGet deviceGet = GsonUtil.gsonIntance().gsonToBean(result, DeviceGet.class);
                        if (deviceGet.getStatus() == 1) {
                            if (deviceGet.getData().size() > 0) {
                                SPUtils.savePreference(NoLoginActivity.this, "isBind", "1");//0 未绑定  1已绑定
                                SPUtils.savePreference(NoLoginActivity.this, "deviceid", deviceGet.getData().get(0).getDevice_id());
                                DialogUtils.showDialog(NoLoginActivity.this,
                                        "提示",
                                        "管理员同意绑定",
                                        new DialogUtils.OnDialogListener() {
                                            @Override
                                            public void confirm() {
                                                goToActivity(MainActivity.class);
                                                ScreenManagerUtils.getInstance().removeActivity(NoLoginActivity.this);
                                            }

                                            @Override
                                            public void cancel() {
                                                goToActivity(MainActivity.class);
                                                ScreenManagerUtils.getInstance().removeActivity(NoLoginActivity.this);
                                            }
                                        });

                                goToActivity(MainActivity.class);
                            } else {

                            }
                            ScreenManagerUtils.getInstance().removeActivity(NoLoginActivity.this);
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

}
