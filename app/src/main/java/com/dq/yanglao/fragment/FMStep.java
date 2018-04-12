package com.dq.yanglao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseFragment;

/**
 * 健康-记步
 * Created by jingang on 2018/4/12.
 */

public class FMStep extends MyBaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_step, null);
        return view;
    }
    public static FMStep newInstance() {
        Bundle bundle = new Bundle();
//        bundle.putString("phone", phone);
//        bundle.putString("token", token);
        FMStep fragment = new FMStep();
        fragment.setArguments(bundle);

        return fragment;
    }
}
