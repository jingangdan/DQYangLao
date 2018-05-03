package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.UserInfo2;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录-忘记密码
 * Created by jingang on 2018/4/21.
 */

public class ForgetPwdActivity extends MyBaseActivity {
    @Bind(R.id.editResPhone)
    EditText editResPhone;
    @Bind(R.id.editResPwd)
    EditText editResPwd;
    @Bind(R.id.editResPwd2)
    EditText editResPwd2;
    @Bind(R.id.editResCode)
    EditText editResCode;
    @Bind(R.id.butResCode)
    Button butResCode;
    @Bind(R.id.butResNext)
    Button butResNext;

    private String PATH_RSA;
    private boolean isClick;
    //控制按钮样式是否改变
    private boolean tag = true;
    //每次验证请求需要间隔60S
    private int i = 60;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_res);
        ButterKnife.bind(this);
        setTvTitle("忘记密码");
        setIvBack();
        butResNext.setText("确定");
    }

    @OnClick({R.id.butResCode, R.id.butResNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.butResCode:
                if (!TextUtils.isEmpty(editResPhone.getText().toString().trim())) {
                    PATH_RSA = "mobile=" + editResPhone.getText().toString() + "&type=repwd";
                    try {
                        PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                        byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                        xUtilsSendSms(Base64Utils.encode(encryptByte).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showMessage("请输入手机号");
                }
                break;
            case R.id.butResNext:
                if (TextUtils.isEmpty(editResPhone.getText().toString().trim())) {
                    showMessage("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(editResPwd.getText().toString().trim())) {
                    showMessage("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(editResPwd2.getText().toString().trim())) {
                    showMessage("请输入确认密码");
                    return;
                }
                if (TextUtils.isEmpty(editResCode.getText())) {
                    showMessage("请输入验证码");
                    return;
                }
                if (!editResPwd.getText().toString().equals(editResPwd2.getText().toString())) {
                    showMessage("确认密码不一致");
                    return;
                }

                if (!isClick) {
                    isClick = true;
                    PATH_RSA = "mobile=" + editResPhone.getText().toString();
                    try {
                        PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                        byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                        xUtilsHasMobile(Base64Utils.encode(encryptByte).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 注册
     *
     * @param sign
     */
    public void xUtilsRegister(String sign) {
        System.out.println("找回密码 = " + HttpPath.USER_REPWD + "sign=" + sign);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.USER_REPWD,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("找回密码 = " + result);
                        UserInfo2 u2 = GsonUtil.gsonIntance().gsonToBean(result, UserInfo2.class);
                        if (u2.getStatus() == 1) {
                            showMessage(u2.getData());
                            finish();
                        }
                        if (u2.getStatus() == 0) {
                            showMessage(u2.getData());
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

    /**
     * 验证手机号是否已经注册
     *
     * @param sign
     */
    public void xUtilsHasMobile(String sign) {
        System.out.println("验证 = " + HttpPath.USER_HASMOBILE + "sign=" + sign);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.USER_HASMOBILE,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        isClick = false;
                        System.out.println("验证 = " + result);
                        UserInfo2 u2 = GsonUtil.gsonIntance().gsonToBean(result, UserInfo2.class);
                        if (u2.getStatus() == 0) {
                            PATH_RSA = "mobile=" + editResPhone.getText().toString() + "&pwd=" + editResPwd.getText().toString() + "&verify=" + editResCode.getText().toString();
                            System.out.println("找回密码 = " + PATH_RSA);
                            try {
                                PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                                byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                                xUtilsRegister(Base64Utils.encode(encryptByte).toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if (u2.getStatus() == 1) {
                            showMessage(u2.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        isClick = false;
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

    /**
     * 发送短信（获取验证码）
     *
     * @param sign
     */
    public void xUtilsSendSms(String sign) {
        System.out.println("发短信 = " + HttpPath.USER_SENDSMS + "sign=" + sign);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.USER_SENDSMS,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("发短信 = " + result);
                        UserInfo2 u2 = GsonUtil.gsonIntance().gsonToBean(result, UserInfo2.class);
                        if (u2.getStatus() == 1) {
                            editResCode.setText(u2.getData());
                            changeBtnGetCode();
                        }
                        if (u2.getStatus() == 0) {
                            showMessage(u2.getData());
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


    /**
     * 改变按钮样式
     */
    private void changeBtnGetCode() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        //如果活动为空
                        if (this == null) {
                            break;
                        }

                        ForgetPwdActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                butResCode.setText("获取验证码(" + i + ")");
                                butResCode.setClickable(false);
                            }
                        });

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;

                if (this != null) {
                    ForgetPwdActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            butResCode.setText("获取验证码");
                            butResCode.setClickable(true);
                        }
                    });
                }
            }
        };
        thread.start();
    }
}
