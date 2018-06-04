package com.dq.yanglao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.bean.Step;
import com.dq.yanglao.bean.UserInfo2;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.TimeUtils;
import com.dq.yanglao.view.StepArcView;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 健康-记步
 * Created by jingang on 2018/4/28.
 */

public class FMMove extends MyBaseFragment {
    @Bind(R.id.tvMoveDate)
    TextView tvMoveDate;
    @Bind(R.id.tvMoveNum)
    TextView tvMoveNum;
    @Bind(R.id.sv)
    StepArcView sv;

    private String step_result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_move, null);
        ButterKnife.bind(this, view);
        sv.setText("步数");
        return view;
    }

    public static FMMove newInstance(String deviceid, String uid, String token) {
        Bundle bundle = new Bundle();
        bundle.putString("deviceid", deviceid);
        bundle.putString("uid", uid);
        bundle.putString("token", token);
        FMMove fragment = new FMMove();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getUserVisibleHint()) {
            getStep();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (getActivity() != null) {
                getStep();
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void getStep() {
        String PATH_RSA = "device_id=" + getArguments().getString("deviceid") + "&uid=" + getArguments().getString("uid") + "&token=" + getArguments().getString("token") + "&date=" + System.currentTimeMillis() / 1000;
        System.out.println("步数 = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            xUtilsGetStep(Base64Utils.encode(encryptByte).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取步数
     *
     * @param sign
     */
    public void xUtilsGetStep(String sign) {
        System.out.println("步数 = " + HttpPath.DEVICE_STEP + "sign=" + sign);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(getActivity(),
                HttpPath.DEVICE_STEP,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        step_result = result;
                        System.out.println("步数 = " + result);
                        Step step = GsonUtil.gsonIntance().gsonToBean(result, Step.class);
                        if (step.getStatus() == 1) {

                            sv.setCurrentCount(1000, Integer.parseInt(step.getData().getStep()));
                            tvMoveNum.setText(step.getData().getStep());
                            tvMoveDate.setText(TimeUtils.getStrTime(step.getData().getS_date()));
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        if (!TextUtils.isEmpty(step_result)) {
                            UserInfo2 u2 = GsonUtil.gsonIntance().gsonToBean(step_result, UserInfo2.class);
                            if (u2.getStatus() == 0) {
                                tvMoveDate.setText(u2.getData());
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

