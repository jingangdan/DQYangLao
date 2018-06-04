package com.dq.yanglao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.DeviceSetting;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.CodeUtils;
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
 * 我的-情景模式
 * Created by jingang on 2018/5/3.
 */

public class ProfileActivity extends MyBaseActivity implements OnCallBackTCP {
    @Bind(R.id.ivProfile1)
    ImageView ivProfile1;
    @Bind(R.id.linProfile1)
    LinearLayout linProfile1;
    @Bind(R.id.ivProfile2)
    ImageView ivProfile2;
    @Bind(R.id.linProfile2)
    LinearLayout linProfile2;
    @Bind(R.id.ivProfile3)
    ImageView ivProfile3;
    @Bind(R.id.linProfile3)
    LinearLayout linProfile3;
    @Bind(R.id.ivProfile4)
    ImageView ivProfile4;
    @Bind(R.id.linProfile4)
    LinearLayout linProfile4;
    @Bind(R.id.butProfile)
    Button butProfile;

    private String profile, profile_msg;
    private String val;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setTvTitle("情景模式");
        setIvBack();

        getDeviceSetting();
    }

    @OnClick({R.id.linProfile1, R.id.linProfile2, R.id.linProfile3, R.id.linProfile4})
    public void onViewClicked(View view) {
        setClearSelect();
        switch (view.getId()) {
            case R.id.linProfile1:
                ivProfile1.setVisibility(View.VISIBLE);
                profile = "1";
                profile_msg = "震动加响铃";
                break;
            case R.id.linProfile2:
                ivProfile2.setVisibility(View.VISIBLE);
                profile = "2";
                profile_msg = "响铃";
                break;
            case R.id.linProfile3:
                ivProfile3.setVisibility(View.VISIBLE);
                profile = "3";
                profile_msg = "震动";
                break;
            case R.id.linProfile4:
                ivProfile4.setVisibility(View.VISIBLE);
                profile = "4";
                profile_msg = "静音";
                break;
//            case R.id.butProfile:
//                //[DQHB*uid*LEN*profile,设备id,模式]
//                if (!TextUtils.isEmpty(profile)) {
//                    MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*profile," + SPUtils.getPreference(this, "deviceid") + "," + profile + "]");
//                }
//                System.out.println("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*profile," + SPUtils.getPreference(this, "deviceid") + "," + profile + "]");
//                break;
        }
    }

    @OnClick(R.id.butProfile)
    public void onViewClicked() {
        //[DQHB*uid*LEN*profile,设备id,模式]
        if (!TextUtils.isEmpty(profile)) {
            MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*profile," + SPUtils.getPreference(this, "deviceid") + "," + profile + "]");
        }
        System.out.println("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*profile," + SPUtils.getPreference(this, "deviceid") + "," + profile + "]");

    }

    private void setClearSelect() {
        ivProfile1.setVisibility(View.INVISIBLE);
        ivProfile2.setVisibility(View.INVISIBLE);
        ivProfile3.setVisibility(View.INVISIBLE);
        ivProfile4.setVisibility(View.INVISIBLE);
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
                            val = setting.getData().get(5).getVal();
                            if (!TextUtils.isEmpty(val)) {
                                setProfile();
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

    public void setProfile() {
        if (val.equals("1")) {
            ivProfile1.setVisibility(View.VISIBLE);
        }
        if (val.equals("2")) {
            ivProfile2.setVisibility(View.VISIBLE);
        }
        if (val.equals("3")) {
            ivProfile3.setVisibility(View.VISIBLE);
        }
        if (val.equals("4")) {
            ivProfile4.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCallback(String type, String msg) {
        System.out.println("msg = " + msg + "   type = " + type);
        if (type.equals("profile")) {
            showMessage("设置成功");
            Intent intent = new Intent();
            intent.putExtra("profile", profile_msg);
            setResult(CodeUtils.ME_PROFILE);
            ProfileActivity.this.finish();
        }
    }

}
