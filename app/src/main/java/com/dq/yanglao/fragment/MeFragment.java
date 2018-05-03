package com.dq.yanglao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.ui.ACTActivity;
import com.dq.yanglao.ui.DeviceActivity;
import com.dq.yanglao.ui.DeviceOffActivity;
import com.dq.yanglao.ui.LoginActivity;
import com.dq.yanglao.ui.ProfileActivity;
import com.dq.yanglao.ui.SosActivity;
import com.dq.yanglao.utils.DialogUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ScreenManagerUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 * Created by jingang on 2018/4/12.
 */
public class MeFragment extends MyBaseFragment {
    @Bind(R.id.linMeSOS)
    LinearLayout linMeSOS;
    @Bind(R.id.butMeOut)
    Button butMeOut;
    @Bind(R.id.linMeDevice)
    LinearLayout linMeDevice;
    @Bind(R.id.linMeProfile)
    LinearLayout linMeProfile;
    @Bind(R.id.linMeACT)
    LinearLayout linMeACT;
    @Bind(R.id.linMeDeviceOff)
    LinearLayout linMeDeviceOff;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_me, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.linMeSOS, R.id.butMeOut, R.id.linMeDevice, R.id.linMeProfile, R.id.linMeACT, R.id.linMeDeviceOff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.linMeSOS:
                startActivity(new Intent(getActivity(), SosActivity.class));
                break;
            case R.id.linMeDevice:
                //设备列表
                startActivity(new Intent(getActivity(), DeviceActivity.class));
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
            case R.id.linMeProfile:
                //情景模式
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                break;
            case R.id.linMeACT:
                //短信提醒设置
                startActivity(new Intent(getActivity(), ACTActivity.class));
                break;
            case R.id.linMeDeviceOff:
                //远程关机
                startActivity(new Intent(getActivity(), DeviceOffActivity.class));
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
