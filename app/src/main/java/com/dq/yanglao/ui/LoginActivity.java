package com.dq.yanglao.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.utils.DensityUtil;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //initWindowss(getResources().getColor(R.color.touming));

        initWatcher();
        etLoginPhone.addTextChangedListener(phone_watcher);
        etLoginPwd.addTextChangedListener(pwd_watcher);

        tvRes.setOnClickListener(new OnClickListener());

    }

    private void initWindowss(int color) {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
            //设置导航栏颜色
            window.setNavigationBarColor(color);
            ViewGroup contentView = ((ViewGroup) findViewById(android.R.id.content));
            View childAt = contentView.getChildAt(0);
            if (childAt != null) {
                childAt.setFitsSystemWindows(true);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置contentview为fitsSystemWindows
            ViewGroup contentView = (ViewGroup) findViewById(android.R.id.content);
            View childAt = contentView.getChildAt(0);
            if (childAt != null) {
                childAt.setFitsSystemWindows(true);
            }
            //给statusbar着色
            View view = new View(this);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(this)));
            view.setBackgroundColor(color);
            contentView.addView(view);
        }
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
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
                startActivity(new Intent(this, MainActivity.class));
                //this.finish();
                break;
//            case R.id.tvRes:
//                startActivity(new Intent(this, ResActivity.class));
//                break;
            case R.id.tvForgetPwd:
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

}
