package com.dq.yanglao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseFragment;

import butterknife.ButterKnife;

/**
 * 我的
 * Created by jingang on 2018/4/12.
 */
public class MeFragment extends MyBaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_location, null);
        ButterKnife.bind(this, view);

        return view;
    }
}
