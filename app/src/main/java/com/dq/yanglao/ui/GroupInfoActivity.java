package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;

/**
 * 群组信息
 * Created by jingang on 2018/4/18.
 */

public class GroupInfoActivity extends MyBaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_chating);
        setTvTitle("群组信息");
        setIvBack();
    }


}
