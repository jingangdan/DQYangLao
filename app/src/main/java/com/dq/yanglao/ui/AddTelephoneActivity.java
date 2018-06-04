package com.dq.yanglao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.bean.PhbList;
import com.dq.yanglao.utils.CodeUtils;
import com.dq.yanglao.utils.ForceExitReceiver;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.SPUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页-电话本-编辑联系人
 * Created by jingang on 2018/4/27.
 */

public class AddTelephoneActivity extends MyBaseActivity implements OnCallBackTCP {
    @Bind(R.id.editAddTelephoneName)
    EditText editAddTelephoneName;
    @Bind(R.id.editAddTelephoneMobile)
    EditText editAddTelephoneMobile;
    @Bind(R.id.butAddTelephone)
    Button butAddTelephone;

    private String phb;
    String s = "";
    private boolean isClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_addtelephone);
        ButterKnife.bind(this);
        setTvTitle("添加联系人");
        setIvBack();
        getIntentResult();

        ForceExitReceiver.setOnClickListenerSOS(this);
    }

    public void getIntentResult() {
        phb = getIntent().getStringExtra("phbList");
        System.out.println("phb = " + phb);
        if (!TextUtils.isEmpty(phb)) {
            PhbList phbList = GsonUtil.gsonIntance().gsonToBean(phb, PhbList.class);
            if (phbList.getData().size() > 0) {
                for (int i = 0; i < phbList.getData().size(); i++) {
                    s = s + phbList.getData().get(i).getMobile() + "," + phbList.getData().get(i).getName() + ",";
                }
            }
        }
    }

    @OnClick(R.id.butAddTelephone)
    public void onViewClicked() {
        if (TextUtils.isEmpty(editAddTelephoneName.getText().toString().trim())) {
            showMessage("请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(editAddTelephoneMobile.getText().toString().trim())) {
            showMessage("请输入电话号码");
            return;
        }
        if (editAddTelephoneName.getText().toString().length() > 10) {
            showMessage("姓名字数超过限制");
            return;
        }
        if (!isMobile(editAddTelephoneMobile.getText().toString().trim())) {
            showMessage("请输入正确的电话号码");
            return;
        }
        if (!isClick) {
            isClick = true;
            //[DQHB*uid*LEN*PHB,device_id,号码,名字,号码,名字,号码,名字,号码,名字,号码,名字]
//            MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*PHB,"
//                    + SPUtils.getPreference(this, "deviceid") + ","
//                    + s + editAddTelephoneMobile.getText().toString().trim() + "," + editAddTelephoneName.getText().toString().trim() + "]");

            MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*PHB,"
                    + SPUtils.getPreference(this, "deviceid") + ","
                    + s + editAddTelephoneMobile.getText().toString().trim() + "," + editAddTelephoneName.getText().toString().trim() + "]");
        }
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    @Override
    public void onCallback(String type, String msg) {
        isClick = false;
        //添加情况
        if (type.equals("PHB")) {
            showMessage("添加成功");
            Intent intent = new Intent();
            setResult(CodeUtils.TELEPHONE_ADD, intent);
            this.finish();
        }
    }
}
