package com.dq.yanglao.ui;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页-设备信息
 * Created by jingang on 2018/4/21.
 */

public class EquipmentActivity extends MyBaseActivity {
    @Bind(R.id.tvEquipBirth)
    TextView tvEquipBirth;
    @Bind(R.id.tvEquipAddr)
    TextView tvEquipAddr;

    private int mYear = 2018, mMonth = 03, mDay = 23;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_equipment);
        ButterKnife.bind(this);
        setIvBack();
    }

    @OnClick({R.id.tvEquipBirth, R.id.tvEquipAddr})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEquipBirth:
                //生日（选择日期）
                pickDate(mYear, mMonth, mDay);
                break;
            case R.id.tvEquipAddr:
                //家庭住址（选择地区）
                break;
        }
    }

    /**
     * 选择日期
     *
     * @param year
     * @param month
     * @param day
     */
    public void pickDate(int year, int month, int day) {
        final DatePickerDialog mDialog = new DatePickerDialog(this, null,
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

                tvEquipBirth.setText(days);
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
}
