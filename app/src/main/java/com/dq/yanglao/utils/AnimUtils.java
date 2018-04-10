package com.dq.yanglao.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.dq.yanglao.R;

/**
 * 动画
 * Created by jingang on 2018/4/10.
 */

public class AnimUtils {
    /**
     * 缩放变大动画
     *
     * @param context
     * @param view 目标view
     */
    public static void startScaleInAnim(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale_in);
        if (view != null)
            view.startAnimation(animation);
    }

    /**
     * 缩放缩小动画
     *
     * @param context
     * @param view 目标view
     */
    public static void startScaleOutAnim(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_scale_out);
        if (view != null)
            view.startAnimation(animation);
    }
}
