package com.dq.yanglao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 健康-记步
 * Created by jingang on 2018/4/28.
 */

public class FMMove extends MyBaseFragment {
    @Bind(R.id.butMoveFront)
    Button butMoveFront;
    @Bind(R.id.tvMoveDate)
    TextView tvMoveDate;
    @Bind(R.id.butMoveAfter)
    Button butMoveAfter;
    @Bind(R.id.tvMoveNum)
    TextView tvMoveNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_move, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public static FMMove newInstance(String deviceid, String uid, String token) {
        Bundle bundle = new Bundle();
        bundle.putString("deviceid", deviceid);
        bundle.putString("uid", uid);
        bundle.putString("token", token);
        FMMove fragment = new FMMove();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.butMoveFront, R.id.tvMoveDate, R.id.butMoveAfter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.butMoveFront:
                break;
            case R.id.tvMoveDate:
                break;
            case R.id.butMoveAfter:
                break;
        }
    }
}

