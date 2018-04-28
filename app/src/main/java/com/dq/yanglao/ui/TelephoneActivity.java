package com.dq.yanglao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;

import com.dq.yanglao.R;
import com.dq.yanglao.adapter.PhbAdapter;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.PhbList;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.CodeUtils;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页-电话本
 * Created by jingang on 2018/4/27.
 */

public class TelephoneActivity extends MyBaseActivity {
    @Bind(R.id.rvTelephone)
    RecyclerView rvTelephone;
    @Bind(R.id.butTelephone)
    Button butTelephone;

    private List<PhbList.DataBean> phbList = new ArrayList<>();
    private PhbAdapter phbAdapter;
    private String PATH_RSA, sign;

    private String phb_result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_telephone);
        ButterKnife.bind(this);
        setTvTitle("电话本");
        setIvBack();
        getPhb();
    }

    @OnClick(R.id.butTelephone)
    public void onViewClicked() {
        if (phbList.size() <= 10) {
            Intent intent = new Intent(this, AddTelephoneActivity.class);
            intent.putExtra("phbList", phb_result);
            startActivityForResult(intent, CodeUtils.TELEPHONE_LIST);
        } else {
            showMessage("最多添加10个");
        }

    }

    public void getPhb() {
        phbAdapter = new PhbAdapter(this, phbList);
        rvTelephone.setLayoutManager(new LinearLayoutManager(this));
        rvTelephone.setAdapter(phbAdapter);

        PATH_RSA = "device_id=" + SPUtils.getPreference(this, "deviceid") + "&uid=" + SPUtils.getPreference(this, "uid") + "&token=" + SPUtils.getPreference(this, "token");
        System.out.println("sos = " + PATH_RSA);
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            sign = Base64Utils.encode(encryptByte).toString();
            xUtilsGetphb(sign);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备通讯录
     *
     * @param sign
     */
    public void xUtilsGetphb(String sign) {
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(this,
                HttpPath.DEVICE_GETPHB,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        phb_result = result;
                        System.out.println("获取设备通讯录 = " + result);
                        PhbList phb = GsonUtil.gsonIntance().gsonToBean(result, PhbList.class);
                        if (phb.getStatus() == 1) {
                            phbList.clear();
                            phbList.addAll(phb.getData());
                            phbAdapter.notifyDataSetChanged();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CodeUtils.TELEPHONE_LIST) {
            if (resultCode == CodeUtils.TELEPHONE_ADD) {
                //
                if (!TextUtils.isEmpty(sign)) {
                    xUtilsGetphb(sign);
                }
            }
        }
    }

}
