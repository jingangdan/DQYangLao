package com.dq.yanglao.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.dq.yanglao.R;
import com.dq.yanglao.ui.MainActivity;

/**
 * Created by jingang on 2018/4/26.
 */

public class ForceExitReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        final Activity activity = ScreenManagerUtils.getmCurrentActivity();
        Log.d("activity", activity + "");

        System.out.println("55555");
        Dialog bottomDialog = new Dialog(activity, R.style.BottomDialog);
        View contentView = LayoutInflater.from(activity).inflate(R.layout.dialog_shouquan, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = activity.getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(activity, 16f);
        params.bottomMargin = DensityUtil.dp2px(activity, 8f);
        contentView.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);

        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }
}
