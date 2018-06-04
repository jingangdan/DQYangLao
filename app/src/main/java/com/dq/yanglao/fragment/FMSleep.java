package com.dq.yanglao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseFragment;

/**
 * 健康-睡眠
 * Created by jingang on 2018/5/5.
 */

public class FMSleep extends MyBaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_step, null);
        return view;
    }

    public static FMSleep newInstance(String deviceid, String uid, String token) {
        Bundle bundle = new Bundle();
        bundle.putString("deviceid", deviceid);
        bundle.putString("uid", uid);
        bundle.putString("token", token);
        FMSleep fragment = new FMSleep();
        fragment.setArguments(bundle);

        return fragment;
    }

}
