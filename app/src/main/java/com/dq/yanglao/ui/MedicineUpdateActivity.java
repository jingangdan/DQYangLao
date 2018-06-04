package com.dq.yanglao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.utils.CodeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页-吃药提醒-编辑提醒
 * Created by jingang on 2018/6/4.
 */

public class MedicineUpdateActivity extends MyBaseActivity {
    @Bind(R.id.tpMedicineUpdate)
    TimePicker tpMedicineUpdate;
    @Bind(R.id.ivMedicineUpdate1)
    ImageView ivMedicineUpdate1;
    @Bind(R.id.relMedicineUpdateTime)
    RelativeLayout relMedicineUpdateTime;
    @Bind(R.id.ivMedicineUpdate2)
    ImageView ivMedicineUpdate2;
    @Bind(R.id.relMedicineUpdateMsg)
    RelativeLayout relMedicineUpdateMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_medicine_update);
        ButterKnife.bind(this);
        setTvTitle("编辑提醒");
        getTvRight().setText("存储");
        getTvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setIvBack();
    }

    @OnClick({R.id.relMedicineUpdateTime, R.id.relMedicineUpdateMsg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relMedicineUpdateTime:
                startActivityForResult(new Intent(this, MedicineUpdateTimeActivity.class), CodeUtils.MEDICINE_LIST);
                break;
            case R.id.relMedicineUpdateMsg:
                break;
        }
    }
}
