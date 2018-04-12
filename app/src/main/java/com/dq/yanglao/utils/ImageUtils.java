package com.dq.yanglao.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;

/**
 * Description：
 * Created by jingang on 2017/11/5.
 */
public class ImageUtils {
    /**
     * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
     */
    public static void loadIntoUseFitWidth(final Context context, final String imageUrl, int emptyImageId, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);

                        return false;
                    }
                })
                .placeholder(emptyImageId)
                .error(errorImageId)
                .into(imageView);

    }

    public static void loadIntoUseFitWidth(final Context context, final String imageUrl, int emptyImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(emptyImageId)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        //获取imageView的宽
                        int imageViewWidth = imageView.getWidth();
                        //计算缩放比例
                        float sy = (float) (imageViewWidth * 0.1) / (float) (width * 0.1);
                        //计算图片等比例放大后的高
                        int imageViewHeight = (int) (height * sy);

                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        params.height = imageViewHeight;
                        imageView.setLayoutParams(params);
                    }
                });

    }
}
