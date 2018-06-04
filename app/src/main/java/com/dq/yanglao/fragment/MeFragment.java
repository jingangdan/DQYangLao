package com.dq.yanglao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.bean.UInfo;
import com.dq.yanglao.ui.ACTActivity;
import com.dq.yanglao.ui.DeviceActivity;
import com.dq.yanglao.ui.DeviceOffActivity;
import com.dq.yanglao.ui.EquipmentActivity;
import com.dq.yanglao.ui.FamilyActivity;
import com.dq.yanglao.ui.LoginActivity;
import com.dq.yanglao.ui.ProfileActivity;
import com.dq.yanglao.ui.SettingActivity;
import com.dq.yanglao.ui.SosActivity;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.CodeUtils;
import com.dq.yanglao.utils.DialogUtils;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ScreenManagerUtils;
import com.dq.yanglao.utils.TimeUtils;
import com.dq.yanglao.view.GlideCircleTransform;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 * Created by jingang on 2018/4/12.
 */
public class MeFragment extends MyBaseFragment {
    @Bind(R.id.ivMe)
    ImageView ivMe;
    @Bind(R.id.tvMeName)
    TextView tvMeName;
    @Bind(R.id.tvMeMobile)
    TextView tvMeMobile;
    @Bind(R.id.relMeSOS)
    RelativeLayout relMeSOS;
    @Bind(R.id.butMeOut)
    Button butMeOut;
    @Bind(R.id.tvMeProfile)
    TextView tvMeProfile;
    @Bind(R.id.linLocationTop)
    LinearLayout linLocationTop;
    @Bind(R.id.relMeDevice)
    RelativeLayout relMeDevice;
    @Bind(R.id.relMeFamily)
    RelativeLayout relMeFamily;
    @Bind(R.id.relMeProfile)
    RelativeLayout relMeProfile;
    @Bind(R.id.relMeACT)
    RelativeLayout relMeACT;
    @Bind(R.id.relMeDeviceOff)
    RelativeLayout relMeDeviceOff;
    @Bind(R.id.relMeParent)
    RelativeLayout relMeParent;
    @Bind(R.id.relMeSetting)
    RelativeLayout relMeSetting;

    private String name, mobile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_me, null);
        ButterKnife.bind(this, view);
        setTopMargin();

        getUInfo();

        initDate();
        return view;
    }

    public void setTopMargin() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(linLocationTop.getLayoutParams());
        lp.setMargins(0, TimeUtils.getStatusBarHeight(getActivity()), 0, 0);
        linLocationTop.setLayoutParams(lp);
    }


    @OnClick({R.id.relMeSOS, R.id.butMeOut, R.id.relMeDevice, R.id.relMeProfile, R.id.relMeACT, R.id.relMeDeviceOff, R.id.relMeFamily,R.id.relMeParent,R.id.relMeSetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relMeSOS:
                startActivity(new Intent(getActivity(), SosActivity.class));
                break;
            case R.id.relMeDevice:
                //设备列表
                startActivity(new Intent(getActivity(), DeviceActivity.class));
                break;
            case R.id.relMeFamily:
                //家庭成员
                startActivity(new Intent(getActivity(), FamilyActivity.class));
                break;
            case R.id.butMeOut:

                DialogUtils.showDialog(getActivity(), "提示：", "确定要退出登录吗？", new DialogUtils.OnDialogListener() {
                    @Override
                    public void confirm() {
                        SPUtils.savePreference(getActivity(), "isLogin", "0");//0 未登录  1已登录
                        SPUtils.savePreference(getActivity(), "isBind", "0");

                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        ScreenManagerUtils.getInstance().removeActivity(getActivity());

//                        MyApplacation.tcpClient.closeSelf();
                        MyApplacation.tcpHelper.closeTCP();

                    }

                    @Override
                    public void cancel() {

                    }
                });
                break;
            case R.id.relMeProfile:
                //情景模式
                startActivityForResult(new Intent(getActivity(), ProfileActivity.class), CodeUtils.FM_ME);
                // startActivity(new Intent(getActivity(), ProfileActivity.class));
                break;
            case R.id.relMeACT:
                //短信提醒设置
                startActivity(new Intent(getActivity(), ACTActivity.class));
                break;
            case R.id.relMeDeviceOff:
                //远程关机
                startActivity(new Intent(getActivity(), DeviceOffActivity.class));
                break;
            case R.id.relMeParent:
                //父母信息
                startActivity(new Intent(getActivity(), EquipmentActivity.class));
                break;
            case R.id.relMeSetting:
                //设置
                startActivity(new Intent(getActivity(), SettingActivity.class).putExtra("name", name).putExtra("mobile", mobile));
                break;
        }

    }

    public void getUInfo() {
        String PATH_RSA = "uid=" + SPUtils.getPreference(getActivity(), "uid") + "&token=" + SPUtils.getPreference(getActivity(), "token");
        System.out.println("sos = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            xUtilsGetUInfo(Base64Utils.encode(encryptByte).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xUtilsGetUInfo(String sign) {
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(getActivity(),
                HttpPath.DEVICE_GETUINFO,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取用户信息 = " + result);
                        UInfo uInfo = GsonUtil.gsonIntance().gsonToBean(result, UInfo.class);
                        if (uInfo.getStatus() == 1) {
                            Glide.with(getActivity())
                                    .load(HttpPath.PATH + uInfo.getData().getHeadimg())
                                    .bitmapTransform(new GlideCircleTransform(getActivity()))
                                    .crossFade(1000)
                                    .error(R.mipmap.ic_launcher)
                                    .into(ivMe);
                            name = uInfo.getData().getName();
                            mobile = uInfo.getData().getMobile();

                            if (TextUtils.isEmpty(uInfo.getData().getName())) {
                                tvMeName.setText("用户" + uInfo.getData().getMobile());
                            } else {
                                tvMeName.setText(uInfo.getData().getName());
                            }
                            tvMeMobile.setText(uInfo.getData().getMobile());

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

    public void initDate() {
        Glide.with(getActivity())
                .load("")
                .bitmapTransform(new GlideCircleTransform(getActivity()))
                .crossFade(1000)
                .error(R.mipmap.ic_launcher)
                .into(ivMe);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.FM_ME) {
            if (resultCode == CodeUtils.ME_PROFILE) {
                tvMeProfile.setText(data.getStringExtra("profile"));

            }
        }
    }
}
