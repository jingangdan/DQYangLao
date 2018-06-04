package com.dq.yanglao.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.OldInfo;
import com.dq.yanglao.bean.UserInfo2;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.view.GlideCircleTransform;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页-设备信息
 * Created by jingang on 2018/4/21.
 */
public class EquipmentActivity extends MyBaseActivity {
    @Bind(R.id.tvEquipBirth)
    TextView tvEquipBirth;
    @Bind(R.id.tvEquipAddr)
    TextView tvEquipAddr;
    @Bind(R.id.editEquipNickname)
    EditText editEquipNickname;
    @Bind(R.id.editEquipHeight)
    EditText editEquipHeight;
    @Bind(R.id.rbEquipMan)
    RadioButton rbEquipMan;
    @Bind(R.id.rbEquipWoman)
    RadioButton rbEquipWoman;
    @Bind(R.id.editEquipRalation)
    EditText editEquipRelation;
    @Bind(R.id.butEquipment)
    Button butEquipment;
    @Bind(R.id.ivEquipHeader)
    ImageView ivEquipHeader;

    private int mYear, mMonth, mDay;

    private String header, nickname, birth, height, sex, relation;
    private boolean isClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_equipment);
        ButterKnife.bind(this);
        setTvTitle("");
        setIvBack();
        getDInfo();

        rbEquipWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = "0";
                rbEquipMan.setChecked(false);
            }
        });
        rbEquipMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sex = "1";
                rbEquipWoman.setChecked(false);
            }
        });
    }

    @OnClick({R.id.tvEquipBirth, R.id.tvEquipAddr, R.id.butEquipment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEquipBirth:
                //生日（选择日期）
                pickDate(mYear, mMonth, mDay);
                break;
            case R.id.tvEquipAddr:
                //家庭住址（选择地区）
                break;
            case R.id.butEquipment:
                //保存
                setDInfo();
                break;
        }
    }

    public void getDInfo() {
        String PATH_RSA = "device_id=" + SPUtils.getPreference(this, "deviceid") + "&uid=" + SPUtils.getPreference(this, "uid") + "&token=" + SPUtils.getPreference(this, "token");
        System.out.println("sos = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            xUtilsGetDInfo(Base64Utils.encode(encryptByte).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDInfo() {
        String PATH_RSA = "device_id=" + SPUtils.getPreference(this, "deviceid") + "&uid=" + SPUtils.getPreference(this, "uid") + "&token=" + SPUtils.getPreference(this, "token")
                + "&birth_y=" + mYear + "&birth_m=" + mMonth + "&birth_d=" + mDay
                + "&img=" + header + "&height=" + height + "&nickname=" + nickname
                + "&sex=" + sex + "&relation=" + relation;
        System.out.println("sos = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            xUtilsSetDInfo(Base64Utils.encode(encryptByte).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xUtilsGetDInfo(String sign) {
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_GETDINFO,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取老人信息 = " + result);
                        OldInfo oldInfo = GsonUtil.gsonIntance().gsonToBean(result, OldInfo.class);
                        if (oldInfo.getStatus() == 1) {
                            header = oldInfo.getData().getImg();
                            nickname = oldInfo.getData().getNickname();
                            if (!TextUtils.isEmpty(oldInfo.getData().getBirth_y())) {
                                mYear = Integer.parseInt(oldInfo.getData().getBirth_y());
                            }
                            if (!TextUtils.isEmpty(oldInfo.getData().getBirth_m())) {
                                mMonth = Integer.parseInt(oldInfo.getData().getBirth_m());
                            }
                            if (!TextUtils.isEmpty(oldInfo.getData().getBirth_d())) {
                                mDay = Integer.parseInt(oldInfo.getData().getBirth_d());
                            }
                            height = oldInfo.getData().getHeight();
                            sex = oldInfo.getData().getSex();
                            relation = oldInfo.getData().getRelation();
                            setInfo();
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

    public void xUtilsSetDInfo(String sign) {
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_SETDINFO,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("设置老人信息 = " + result);
                        UserInfo2 u2 = GsonUtil.gsonIntance().gsonToBean(result, UserInfo2.class);
                        if (u2.getStatus() == 1) {
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

    public void setInfo() {
        Glide.with(this)
                .load(HttpPath.PATH + header)
                .bitmapTransform(new GlideCircleTransform(this))
                .crossFade(1000)
                .error(R.mipmap.ic_launcher)
                .into(ivEquipHeader);
        editEquipNickname.setText(nickname);
        tvEquipBirth.setText(mYear + "年" + mMonth + "月" + mDay + "日");

        editEquipHeight.setText(height);

        if (sex.equals("0")) {
            rbEquipWoman.setChecked(true);
        }
        if (sex.equals("1")) {
            rbEquipMan.setChecked(true);
        }
        editEquipRelation.setText(relation);
    }

    /**
     * 选择日期
     *
     * @param year
     * @param month
     * @param day
     */
    public void pickDate(int year, int month, int day) {
        final DatePickerDialog mDialog = new DatePickerDialog(this, null,
                year, month, day);

        //手动设置按钮
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                mYear = datePicker.getYear();
                mMonth = datePicker.getMonth();
                mDay = datePicker.getDayOfMonth();
                String days;
                if (mMonth + 1 < 10) {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("年").append("0").
                                append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("年").append("0").
                                append(mMonth + 1).append("月").append(mDay).append("日").toString();
                    }

                } else {
                    if (mDay < 10) {
                        days = new StringBuffer().append(mYear).append("年").
                                append(mMonth + 1).append("月").append("0").append(mDay).append("日").toString();
                    } else {
                        days = new StringBuffer().append(mYear).append("年").
                                append(mMonth + 1).append("月").append(mDay).append("日").toString();
                    }

                }

                tvEquipBirth.setText(days);
            }
        });
        //取消按钮，如果不需要直接不设置即可
        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("BUTTON_NEGATIVE~~");
            }
        });

        mDialog.show();
    }
}
