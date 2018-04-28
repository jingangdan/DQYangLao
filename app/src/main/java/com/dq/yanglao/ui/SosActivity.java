package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dq.yanglao.Interface.OnClickListenerSOS;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.SosList;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.ForceExitReceiver;
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
 * 设置亲情号码
 * Created by jingang on 2018/4/26.
 */

public class SosActivity extends MyBaseActivity implements OnClickListenerSOS {
    @Bind(R.id.editSos1)
    EditText editSos1;
    @Bind(R.id.editSos2)
    EditText editSos2;
    @Bind(R.id.editSos3)
    EditText editSos3;
    @Bind(R.id.butSos)
    Button butSos;

    private String phone1, phone2, phone3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_sos);
        ButterKnife.bind(this);
        setTvTitle("亲亲号码");
        setIvBack();
        ForceExitReceiver.setOnClickListenerSOS(this);

        getSos();

    }

    @OnClick(R.id.butSos)
    public void onViewClicked() {
        phone1 = editSos1.getText().toString().trim();
        phone2 = editSos2.getText().toString().trim();
        phone3 = editSos3.getText().toString().trim();

        if (!TextUtils.isEmpty(phone1)) {
            //[DQHB*uid*LEN*SOS1,device_id,号码]
            MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*SOS1," + SPUtils.getPreference(this, "deviceid") + "," + phone1 + "]");
        }
        if (!TextUtils.isEmpty(phone2)) {
            //[DQHB*uid*LEN*SOS1,device_id,号码]
            MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*SOS2," + SPUtils.getPreference(this, "deviceid") + "," + phone2 + "]");
        }

        if (!TextUtils.isEmpty(phone3)) {
            //[DQHB*uid*LEN*SOS1,device_id,号码]
            MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*SOS3," + SPUtils.getPreference(this, "deviceid") + "," + phone3 + "]");
        }

    }

    @Override
    public void onClickSOS(String msg) {
        if (msg.equals("SOS1") || msg.equals("SOS2") || msg.equals("SOS3")) {
            showMessage("设置成功");
        } else {
            showMessage("设置失败");
        }
    }

    public void getSos() {
        String PATH_RSA = "device_id=" + SPUtils.getPreference(this, "deviceid") + "&uid=" + SPUtils.getPreference(this, "uid") + "&token=" + SPUtils.getPreference(this, "token");
        System.out.println("sos = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            xUtilsGetSos(Base64Utils.encode(encryptByte).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备SOS
     *
     * @param sign
     */
    public void xUtilsGetSos(String sign) {
        System.out.println("获取设备SOS = " + HttpPath.DEVICE_GETSOS + "sign=" + sign);
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_GETSOS,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("获取设备SOS = " + result);
                        SosList sosList = GsonUtil.gsonIntance().gsonToBean(result, SosList.class);
                        if (sosList.getStatus() == 1) {
                            if (sosList.getData().size() == 1) {
                                editSos1.setText(sosList.getData().get(0).getMobile());
                            }
                            if (sosList.getData().size() == 2) {
                                editSos1.setText(sosList.getData().get(0).getMobile());
                                editSos2.setText(sosList.getData().get(1).getMobile());
                            }
                            if (sosList.getData().size() == 3) {
                                editSos1.setText(sosList.getData().get(0).getMobile());
                                editSos2.setText(sosList.getData().get(1).getMobile());
                                editSos3.setText(sosList.getData().get(2).getMobile());
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
}
