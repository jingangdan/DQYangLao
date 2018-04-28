package com.dq.yanglao.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.dq.yanglao.Interface.OnClickListenerSOS;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.bean.Heart;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.ForceExitReceiver;
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
 * 健康-心率
 * Created by jingang on 2018/4/12.
 */

public class FMHeartBeat extends MyBaseFragment implements OnClickListenerSOS {
    @Bind(R.id.butHeartBeatFront)
    Button butHeartBeatFront;
    @Bind(R.id.tvHeartBeatDate)
    TextView tvHeartBeatDate;
    @Bind(R.id.butHeartBeatAfter)
    Button butHeartBeatAfter;
    @Bind(R.id.tvHeartBeatNum)
    TextView tvHeartBeatNum;
    @Bind(R.id.butHeartMeasure)
    Button butHeartMeasure;

    private int mYear = 2018, mMonth = 03, mDay = 28;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_heartbeat, null);
        ButterKnife.bind(this, view);

        ForceExitReceiver.setOnClickListenerSOS(this);

        return view;
    }

    public static FMHeartBeat newInstance(String deviceid, String uid, String token) {
        Bundle bundle = new Bundle();
        bundle.putString("deviceid", deviceid);
        bundle.putString("uid", uid);
        bundle.putString("token", token);
        FMHeartBeat fragment = new FMHeartBeat();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getHeart(getArguments().getString("deviceid"), getArguments().getString("uid"), getArguments().getString("token"));
        } else {

        }

    }

    @OnClick({R.id.butHeartBeatFront, R.id.tvHeartBeatDate, R.id.butHeartBeatAfter, R.id.butHeartMeasure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.butHeartBeatFront:
                break;
            case R.id.tvHeartBeatDate:
                break;
            case R.id.butHeartBeatAfter:
                break;
            case R.id.butHeartMeasure:
                //测量
                //[DQHB*uid*LEN*hrtstart,1]
                MyApplacation.tcpClient.send("[DQHB*" + getArguments().getString("uid") + "*16*hrtstart," + getArguments().getString("deviceid") + ",1");

                break;
        }
    }

    public void getHeart(String deviceid, String uid, String token) {
        String PATH_RSA = "device_id=" + deviceid + "&uid=" + uid + "&token=" + token;
        System.out.println("心率 = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            xUtilsGetLastHeart(Base64Utils.encode(encryptByte).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取上次检测心率
     *
     * @param sign
     */
    public void xUtilsGetLastHeart(String sign) {
        System.out.println("心率 = " + HttpPath.DEVICE_HEART + "sign=" + sign);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(getActivity(),
                HttpPath.DEVICE_HEART,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("心率= " + result);
                        Heart heart = GsonUtil.gsonIntance().gsonToBean(result, Heart.class);
                        if (heart.getStatus() == 1) {
                            tvHeartBeatNum.setText(heart.getData().getBmp());
                            tvHeartBeatDate.setText(heart.getData().getAddtime());
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
     * 选择日期
     *
     * @param year
     * @param month
     * @param day
     */
    public void pickDate(int year, int month, int day) {
        final DatePickerDialog mDialog = new DatePickerDialog(getActivity(), null,
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

                tvHeartBeatDate.setText(days);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClickSOS(String msg) {
        System.out.println("11111111111111 = " + msg);
    }
}
