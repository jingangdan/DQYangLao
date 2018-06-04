package com.dq.yanglao.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dq.yanglao.Interface.OnItemClickListenerHeather;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.fragment.HealthyFragment;
import com.dq.yanglao.fragment.HomeFragment;
import com.dq.yanglao.fragment.LocationFragment;
import com.dq.yanglao.fragment.MeFragment;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.feedback.PgyFeedback;
import com.pgyersdk.feedback.PgyFeedbackShakeManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主函数
 * Created by jingang on 2018/4/12.
 */

public class MainActivity extends MyBaseActivity {
    @Bind(R.id.main_fl_content)
    FrameLayout mainFlContent;
    @Bind(R.id.main_view_line)
    TextView mainViewLine;
    @Bind(R.id.main_iv_1)
    ImageView mainIv1;
    @Bind(R.id.main_tv_1)
    TextView mainTv1;
    @Bind(R.id.main_ll_1)
    LinearLayout mainLl1;
    @Bind(R.id.main_iv_2)
    ImageView mainIv2;
    @Bind(R.id.main_tv_2)
    TextView mainTv2;
    @Bind(R.id.main_ll_2)
    LinearLayout mainLl2;
    @Bind(R.id.main_iv_3)
    ImageView mainIv3;
    @Bind(R.id.main_tv_3)
    TextView mainTv3;
    @Bind(R.id.main_ll_3)
    LinearLayout mainLl3;
    @Bind(R.id.main_iv_4)
    ImageView mainIv4;
    @Bind(R.id.main_tv_4)
    TextView mainTv4;
    @Bind(R.id.main_ll_4)
    LinearLayout mainLl4;
    @Bind(R.id.main_iv_5)
    ImageView mainIv5;
    @Bind(R.id.main_iv_red_dot)
    ImageView mainIvRedDot;
    @Bind(R.id.main_rl_5)
    RelativeLayout mainRl5;
    @Bind(R.id.main_tv_5)
    TextView mainTv5;
    @Bind(R.id.main_ll_5)
    LinearLayout mainLl5;
    @Bind(R.id.main_ll_bottom)
    LinearLayout mainLlBottom;

    private HomeFragment homeFragment;
    private HealthyFragment healthyFragment;
   // private WorkListFragment workListFragment;
    private LocationFragment locationFragment;
    private MeFragment meFragment;
    private Fragment[] fragments;

    private int index = 0;//点击的页卡索引
    private int currentTabIndex = 0;//当前的页卡索引

    private int pages = 0;

    public static Context mContext;

    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getStatusBarHeight(this);

        mContext = this;
        getWindow().setFormat(PixelFormat.TRANSLUCENT);

        initData();
       // setFragment();

        PgyCrashManager.register(this);

        PgyFeedback.getInstance().setMoreParam("tao", "PgyFeedbackShakeManager");

        PgyFeedbackShakeManager.setShakingThreshold(1000);

        PgyUpdateManager.register(MainActivity.this,
                new UpdateManagerListener() {
                    @Override
                    public void onUpdateAvailable(final String result) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("更新")
                                .setMessage("有新版本上线，是否更新？")
                                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String url;
                                        JSONObject jsonData;
                                        try {
                                            jsonData = new JSONObject(
                                                    result);
                                            if ("0".equals(jsonData.getString("code"))) {
                                                JSONObject jsonObject = jsonData
                                                        .getJSONObject("data");
                                                url = jsonObject
                                                        .getString("downloadURL");

                                                startDownloadTask(
                                                        MainActivity.this,
                                                        url);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).show();

                    }

                    @Override
                    public void onNoUpdateAvailable() {
                        //Toast.makeText(getApplicationContext(), "没有更新", Toast.LENGTH_SHORT).show();
                    }
                });

        //动态请求权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSIONS);
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            }
        }

    }

    public void initData() {
        homeFragment = new HomeFragment();
       // workListFragment = new WorkListFragment();
        healthyFragment = new HealthyFragment();
        locationFragment = new LocationFragment();
        meFragment = new MeFragment();
        fragments = new Fragment[]{homeFragment, healthyFragment, locationFragment, meFragment};
        setBottomColor();
        getSupportFragmentManager().beginTransaction().add(R.id.main_fl_content, fragments[index]).show(fragments[index]).commit();
    }


    @SuppressLint("NewApi")
    @OnClick({R.id.main_ll_1, R.id.main_ll_2, R.id.main_ll_3, R.id.main_ll_4, R.id.main_ll_5})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.main_ll_1:
                //首页
                index = 0;
                fragmentControl();
                break;
            case R.id.main_ll_2:
                //健康
                index = 1;
                fragmentControl();
                break;
            case R.id.main_ll_3:
                //定位
                index = 2;
                fragmentControl();
                break;
//            case R.id.main_ll_4:
//                //论坛
//                index = 3;
//                fragmentControl();
//                break;
            case R.id.main_ll_5:
                //我的
                index = 3;
                fragmentControl();
                break;
        }
    }

    /**
     * 控制fragment的变化
     */
    public void fragmentControl() {
        if (currentTabIndex != index) {
            removeBottomColor();
            setBottomColor();

            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.main_fl_content, fragments[index]);
            }
            trx.show(fragments[index]).commit();

           // healthyFragment.setTabPage(page);
            currentTabIndex = index;
        }
    }

    /**
     * 设置底部栏按钮变色
     */
    private void setBottomColor() {
        switch (index) {
            case 0:
                mainIv1.setImageResource(R.mipmap.ic_fm_homepage002);
                mainTv1.setTextColor(getResources().getColor(R.color.main_color));
                break;
            case 1:
                mainIv2.setImageResource(R.mipmap.ic_fm_healthy002);
                mainTv2.setTextColor(getResources().getColor(R.color.main_color));
                break;
            case 2:
                mainIv3.setImageResource(R.mipmap.ic_fm_location002);
                mainTv3.setTextColor(getResources().getColor(R.color.main_color));
                break;
//            case 3:
//                mainIv4.setImageResource(R.mipmap.ic_launcher);
//                mainTv4.setTextColor(getResources().getColor(R.color.main_color));
//                break;
            case 3:
                mainIv5.setImageResource(R.mipmap.ic_fm_me002);
                mainTv5.setTextColor(getResources().getColor(R.color.main_color));
                break;
        }
    }

    /**
     * 清除底部栏颜色
     */
    private void removeBottomColor() {
        switch (currentTabIndex) {
            case 0:
                mainIv1.setImageResource(R.mipmap.ic_fm_homepage001);
                mainTv1.setTextColor(getResources().getColor(R.color.text_color2));
                break;
            case 1:
                mainIv2.setImageResource(R.mipmap.ic_fm_healthy001);
                mainTv2.setTextColor(getResources().getColor(R.color.text_color2));
                break;
            case 2:
                mainIv3.setImageResource(R.mipmap.ic_fm_location001);
                mainTv3.setTextColor(getResources().getColor(R.color.text_color2));
                break;
//            case 3:
//                mainIv4.setImageResource(R.mipmap.ic_launcher);
//                mainTv4.setTextColor(getResources().getColor(R.color.text_color2));
//                break;
            case 3:
                mainIv5.setImageResource(R.mipmap.ic_fm_me001);
                mainTv5.setTextColor(getResources().getColor(R.color.text_color2));
                break;
        }
    }

    /*跳转到fragment*/
//    public void setFragment() {
//        homeFragment.setOnItemClickListener(new OnItemClickListenerHeather() {
//            @Override
//            public void onItemClick(View view, int position, int page) {
//
//                index = position;
//                pages = page;
//                fragmentControl();
//            }
//        });
//
//        healthyFragment.setOnClickListeners(new OnClickListeners() {
//            @Override
//            public void onItemClick(int position) {
//                pages = position;
//                fragmentControl(pages);
//            }
//        });
//    }
}
