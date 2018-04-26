package com.dq.yanglao.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 首页-微聊
 * Created by jingang on 2018/4/16.
 */

public class ChatingActivity extends MyBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_chating);
        setTvTitle("会话群组");

        String Token = "/ULK+E4r6a1957aCkuRkRiSB4bLWDSrHG4nzWhki5wvCULx9Pxj0Ivfk7AnAaYW6o155dj46Slg=";//test

        /**
         * IMKit SDK调用第二步
         *
         * 建立与服务器的连接
         */
        RongIM.connect(Token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                //Connect Token 失效的状态处理，需要重新获取 Token
            }

            @Override
            public void onSuccess(String userId) {
                Log.e("MainActivity", "——onSuccess—-" + userId);
                ChatingActivity.this.finish();

                //RongIM.getInstance().refreshUserInfoCache(new UserInfo1(userId, NickName, Uri.parse(AppConstant.RequestPath.BASE_API_URL + Photo)));
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("MainActivity", "——onError—-" + errorCode);
            }
        });


        /**
         * 启动单聊
         * context - 应用上下文。
         * targetUserId - 要与之聊天的用户 Id。
         * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
         */
//        if (RongIM.getInstance() != null) {
//            RongIM.getInstance().startPrivateChat(this, "1", "hello");
//            this.finish();
//        }

        /**
         * 启动群组聊天界面。
         * @param context       应用上下文。
         * @param targetGroupId 要聊天的群组 Id。
         * @param title         聊天的标题，开发者可以在聊天界面通过 intent.getData().getQueryParameter("title") 获取该值, 再手动设置为标题。
         */
        if (RongIM.getInstance() != null) {
            RongIM.getInstance().startGroupChat(this, "2", "123");
            this.finish();
        }

    }
}
