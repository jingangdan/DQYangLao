package com.dq.yanglao.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;

/**
 * 群聊会话界面
 * Created by jingang on 2018/4/16.
 */

public class ConversationActivity extends MyBaseActivity {
    private static final String TAG = ConversationActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_conversation);
        setTvTitle("测试群聊列表");
        setIvBack();
        getIvRight().setImageResource(R.mipmap.ic_launcher);
        getIvRight().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取群组消息
                startActivity(new Intent(ConversationActivity.this, GroupInfoActivity.class));
            }
        });
    }
}
