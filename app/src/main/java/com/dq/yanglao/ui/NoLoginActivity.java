package com.dq.yanglao.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.DeviceBind;
import com.dq.yanglao.bean.UserInfo2;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ScreenManagerUtils;

import org.xutils.common.Callback;

import java.lang.ref.WeakReference;
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

public class NoLoginActivity extends MyBaseActivity {
    @Bind(R.id.butJoin)
    Button butJoin;

    public static Context context;
    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private Message message;

    @Bind(R.id.editJoin)
    EditText editJoin;

    private String device, uid, PATH_RSA;
    private String bind_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_nologin);
        ButterKnife.bind(this);
        setTvTitle("未绑定设备");
        setIvBack();

        context = this;
        bindReceiver();
    }

    @OnClick(R.id.butJoin)
    public void onViewClicked() {
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

    }

    private class MyHandler extends Handler {
        private WeakReference<NoLoginActivity> mActivity;

        MyHandler(NoLoginActivity activity) {
            mActivity = new WeakReference<NoLoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null) {
                switch (msg.what) {
                    case 1:
                        //txtRcv.append(msg.obj.toString());
                        System.out.println("接收 = " + msg.obj.toString());

                        break;
                    case 2:
                        //txtSend.append(msg.obj.toString());
                        //System.out.println("发送 = " + msg.obj.toString());
                        break;
                }
            }
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction) {
                case "tcpClientReceiver":
                    String msg = intent.getStringExtra("tcpClientReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = msg;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }

    private void bindReceiver() {
        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        registerReceiver(myBroadcastReceiver, intentFilter);
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
                            }
                            if (device.getData().getIs_primary() == 0) {
                                MyApplacation.exec.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        message = Message.obtain();
                                        message.what = 2;
                                        myHandler.sendMessage(message);
                                        MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(NoLoginActivity.this, "uid") + "*16" + "*APPLY," + device.getData().getId() + "]");
                                    }
                                });
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

}
