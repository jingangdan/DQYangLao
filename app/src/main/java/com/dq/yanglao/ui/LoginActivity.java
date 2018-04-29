package com.dq.yanglao.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.DeviceGet;
import com.dq.yanglao.bean.UserInfo1;
import com.dq.yanglao.bean.UserInfo2;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.DensityUtil;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ScreenManagerUtils;
import com.dq.yanglao.view.TcpClient;

import org.xutils.common.Callback;

import java.net.URLEncoder;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 * Created by jingang on 2018/4/9.
 */

public class LoginActivity extends MyBaseActivity {
    @Bind(R.id.etLoginPhone)
    EditText etLoginPhone;
    @Bind(R.id.ivLoginPhoneClear)
    ImageView ivLoginPhoneClear;
    @Bind(R.id.etLoginPwd)
    EditText etLoginPwd;
    @Bind(R.id.ivLoginPwdClear)
    ImageView ivLoginPwdClear;
    @Bind(R.id.ivLoginPwdEye)
    ImageView ivLoginPwdEye;
    @Bind(R.id.butLogin)
    Button butLogin;
    @Bind(R.id.tvRes)
    TextView tvRes;
    @Bind(R.id.tvForgetPwd)
    TextView tvForgetPwd;

    private LoginActivity TAG = LoginActivity.this;

    private TextWatcher phone_watcher, pwd_watcher;

    private LinearLayout parent;
    private Button butEmail, butPhone, butCancel;
    private String phone, pwd, PATH_RSA;

    private String login_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initWatcher();
        etLoginPhone.addTextChangedListener(phone_watcher);
        etLoginPwd.addTextChangedListener(pwd_watcher);

//        tvRes.setOnClickListener(new OnClickListener());

    }

    @OnClick({R.id.ivLoginPhoneClear, R.id.ivLoginPwdClear, R.id.ivLoginPwdEye, R.id.butLogin, R.id.tvRes, R.id.tvForgetPwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivLoginPhoneClear:
                //清除用户输入
                etLoginPhone.setText("");
                etLoginPwd.setText("");
                break;
            case R.id.ivLoginPwdClear:
                etLoginPwd.setText("");
                break;
            case R.id.ivLoginPwdEye:
                //密码是否可见
                if (etLoginPwd.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                etLoginPwd.setSelection(etLoginPwd.getText().toString().length());
                break;
            case R.id.butLogin:
                phone = etLoginPhone.getText().toString().trim();
                pwd = etLoginPwd.getText().toString().trim();

                if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd)) {
                    PATH_RSA = "phone=" + phone + "&pwd=" + pwd;
                    try {
                        PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                        byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                        //login(URLEncoder.encode(Base64Utils.encode(encryptByte).toString(), "UTF-8"));
                        login(Base64Utils.encode(encryptByte).toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    showMessage("账号或密码未输入");
                }

                break;
            case R.id.tvRes:
                startActivity(new Intent(this, ResActivity.class));
                break;
            case R.id.tvForgetPwd:
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
        }
    }

    /**
     * 手机号，密码输入控件公用这一个watcher
     */
    private void initWatcher() {
        phone_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("WrongConstant")
            public void afterTextChanged(Editable s) {
                etLoginPwd.setText("");
                if (s.toString().length() > 0) {
                    ivLoginPhoneClear.setVisibility(View.VISIBLE);
                } else {
                    ivLoginPhoneClear.setVisibility(View.INVISIBLE);
                }
            }
        };

        pwd_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("WrongConstant")
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    ivLoginPwdClear.setVisibility(View.VISIBLE);
                } else {
                    ivLoginPwdClear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    /**
     * 选择注册方式
     */
    public class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            final Dialog bottomDialog = new Dialog(TAG, R.style.BottomDialog);
            View contentView = LayoutInflater.from(TAG).inflate(R.layout.dialog_login_restype, null);
            bottomDialog.setContentView(contentView);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
            params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(TAG, 16f);
            params.bottomMargin = DensityUtil.dp2px(TAG, 8f);
            contentView.setLayoutParams(params);
            bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
            bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
            bottomDialog.show();

            parent = (LinearLayout) bottomDialog.findViewById(R.id.parent);
            butEmail = (Button) bottomDialog.findViewById(R.id.butEmail);
            butPhone = (Button) bottomDialog.findViewById(R.id.butPhone);
            butCancel = (Button) bottomDialog.findViewById(R.id.butCancel);

            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomDialog.dismiss();
                }
            });

            butEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(TAG, ResActivity.class));
                    bottomDialog.dismiss();
                }
            });

            butPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(TAG, ResActivity.class));
                    bottomDialog.dismiss();
                }
            });

            butCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomDialog.dismiss();
                }
            });

        }
    }


    /**
     * 登录
     *
     * @param afterencrypt
     */
    public void login(String afterencrypt) {
        System.out.println("登录 = " + HttpPath.USER_LOGIN + "sign=" + afterencrypt);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", afterencrypt);
        HttpxUtils.Post(this,
                HttpPath.USER_LOGIN,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("登录 = " + result);
                        login_result = result;
                        UserInfo1 u1 = GsonUtil.gsonIntance().gsonToBean(result, UserInfo1.class);
                        if (u1.getStatus() == 1) {
                            SPUtils.savePreference(LoginActivity.this, "isLogin", "1");//0 未登录  1已登录
                            SPUtils.savePreference(LoginActivity.this, "uid", u1.getData().getId());
                            SPUtils.savePreference(LoginActivity.this, "token", u1.getData().getToken());//用户id

//                            MyApplacation.exec = Executors.newCachedThreadPool();
//                            MyApplacation.tcpClient = new TcpClient("47.52.199.154", 49152, MyApplacation.context);
//                            MyApplacation.exec.execute(MyApplacation.tcpClient);

                            if (MyApplacation.tcpHelper == null) {
                                MyApplacation applacation = new MyApplacation();
                                applacation.startTCP();
                            }

                            if (!TextUtils.isEmpty(u1.getData().getId())) {
                                PATH_RSA = "uid=" + u1.getData().getId() + "&token=" + u1.getData().getToken();
                                try {
                                    PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                                    byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                                    getDevice(Base64Utils.encode(encryptByte).toString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if (!TextUtils.isEmpty(login_result)) {
                            UserInfo2 u2 = GsonUtil.gsonIntance().gsonToBean(login_result, UserInfo2.class);
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
                                SPUtils.savePreference(LoginActivity.this, "isBind", "1");//0 未绑定  1已绑定
                                SPUtils.savePreference(LoginActivity.this, "deviceid", deviceGet.getData().get(0).getDevice_id());
                                goToActivity(MainActivity.class);
                            } else {
                                goToActivity(NoLoginActivity.class);
                            }
                            ScreenManagerUtils.getInstance().removeActivity(LoginActivity.this);
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
