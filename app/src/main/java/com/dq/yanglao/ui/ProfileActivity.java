package com.dq.yanglao.ui;

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
import com.dq.yanglao.utils.SPUtils;

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

    private String profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setTvTitle("情景模式");
        setIvBack();
    }

    @OnClick({R.id.linProfile1, R.id.linProfile2, R.id.linProfile3, R.id.linProfile4, R.id.butProfile})
    public void onViewClicked(View view) {
        setClearSelect();
        switch (view.getId()) {
            case R.id.linProfile1:
                ivProfile1.setVisibility(View.VISIBLE);
                profile = "1";
                break;
            case R.id.linProfile2:
                ivProfile2.setVisibility(View.VISIBLE);
                profile = "2";
                break;
            case R.id.linProfile3:
                ivProfile3.setVisibility(View.VISIBLE);
                profile = "3";
                break;
            case R.id.linProfile4:
                ivProfile4.setVisibility(View.VISIBLE);
                profile = "4";
                break;
            case R.id.butProfile:
                //[DQHB*uid*LEN*profile,设备id,模式]
                if (!TextUtils.isEmpty(profile)) {
                    MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(this, "uid") + "*16*profile," + SPUtils.getPreference(this, "deviceid" + "," + profile + "]"));
                }
                break;
        }
    }

    private void setClearSelect() {
        ivProfile1.setVisibility(View.INVISIBLE);
        ivProfile2.setVisibility(View.INVISIBLE);
        ivProfile3.setVisibility(View.INVISIBLE);
        ivProfile4.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCallback(String type, String msg) {
        System.out.println("msg = " + msg + "   type = " + type);
    }
}
