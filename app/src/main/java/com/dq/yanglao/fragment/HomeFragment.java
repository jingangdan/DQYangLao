package com.dq.yanglao.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.Interface.OnClickListeners;
import com.dq.yanglao.Interface.OnItemClickListener;
import com.dq.yanglao.Interface.OnItemClickListenerHeather;
import com.dq.yanglao.R;
import com.dq.yanglao.adapter.HomeDeviceAdapter;
import com.dq.yanglao.adapter.SimpleFragmentPagerAdapter;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.bean.DeviceGet;
import com.dq.yanglao.ui.MedicineActivity;
import com.dq.yanglao.ui.TelephoneActivity;
import com.dq.yanglao.utils.Base64Utils;
import com.dq.yanglao.utils.DensityUtil;
import com.dq.yanglao.utils.ForceExitReceiver;
import com.dq.yanglao.utils.GsonUtil;
import com.dq.yanglao.utils.HttpPath;
import com.dq.yanglao.utils.HttpxUtils;
import com.dq.yanglao.utils.RSAUtils;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.TimeUtils;
import com.dq.yanglao.utils.ToastUtils;
import com.dq.yanglao.view.NoScrollViewPager;
import com.dq.yanglao.view.rollpagerview.ImageLoopAdapter;
import com.dq.yanglao.view.rollpagerview.RollPagerView;

import org.xutils.common.Callback;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 * Created by jingang on 2018/4/12.
 */
public class HomeFragment extends MyBaseFragment implements OnCallBackTCP, ViewPager.OnPageChangeListener {
    @Bind(R.id.rpvHome)
    RollPagerView rpvHome;
    @Bind(R.id.nsvpHome)
    NoScrollViewPager nsvpHome;
    @Bind(R.id.ivHome1)
    ImageView ivHome1;
    @Bind(R.id.ivHome2)
    ImageView ivHome2;
    @Bind(R.id.ivHome3)
    ImageView ivHome3;
    @Bind(R.id.ivHome4)
    ImageView ivHome4;
    @Bind(R.id.ivHomeDevice)
    ImageView ivHomeDevice;
    @Bind(R.id.tlHome)
    TabLayout tlHome;
    @Bind(R.id.linHomeTop)
    LinearLayout linHomeTop;
    private OnItemClickListenerHeather onItemClickListenerHeather;//2、定义接口成员变量
    private String uid, deviceid, token;

    private List<DeviceGet.DataBean> deviceList = new ArrayList<>();

    private String[] titles = new String[]{"计步", "心率", "睡眠"};
    private List<Fragment> fragments = new ArrayList<>();

    private SimpleFragmentPagerAdapter sfpAdapter;
    private int page = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_home2, null);
        ButterKnife.bind(this, view);
        setTopMargin();

        uid = SPUtils.getPreference(getActivity(), "uid");
        deviceid = SPUtils.getPreference(getActivity(), "deviceid");
        setFragment(page);


        initData();

        getData();

        getDevice();

        ForceExitReceiver.setOnClickListenerSOS(this);

        return view;
    }

    public void setTopMargin() {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(linHomeTop.getLayoutParams());
        lp.setMargins(0, TimeUtils.getStatusBarHeight(getActivity()), 0, 0);
        linHomeTop.setLayoutParams(lp);
    }

    public void setFragment(int page) {

        tlHome.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.touming));

        deviceid = SPUtils.getPreference(getActivity(), "deviceid");
        uid = SPUtils.getPreference(getActivity(), "uid");
        token = SPUtils.getPreference(getActivity(), "token");

        fragments.add(FMMove.newInstance(deviceid, uid, token));
        fragments.add(FMHeartBeat.newInstance(deviceid, uid, token));
        fragments.add(FMSleep.newInstance(deviceid, uid, token));

        sfpAdapter = new SimpleFragmentPagerAdapter(getActivity().getSupportFragmentManager(), getActivity(), fragments, titles);
        nsvpHome.setAdapter(sfpAdapter);

        nsvpHome.setCurrentItem(page);
        nsvpHome.setOffscreenPageLimit(titles.length);
        tlHome.setupWithViewPager(nsvpHome);

        nsvpHome.setOnPageChangeListener(this);
    }

    private float mDensity;

    private void getData() {
        //获取屏幕显示密度
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mDensity = metrics.density;
    }

    /**
     * 显示用户操作弹窗
     */
    private void showUserOperationMenu(final List<DeviceGet.DataBean> deviceList) {
        //加载弹窗的布局XML文件
        final View pwView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_select_device, null);
        //初始化弹窗中的控件
        RecyclerView rv = (RecyclerView) pwView.findViewById(R.id.rvDialogHome);
        rv.setLayoutManager(new LinearLayoutManager(pwView.getContext()));
        HomeDeviceAdapter mAdapter = new HomeDeviceAdapter(pwView.getContext(), deviceList);
        rv.setAdapter(mAdapter);
        // rv.setAdapter();

        // 创建弹窗，popupwindow中的listview能响应事件需将touchable和focusable均设为true才行！！！
        final PopupWindow pwForUserOperationMenu = new PopupWindow(pwView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 点击外部后，窗口消失
        pwForUserOperationMenu.setBackgroundDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(null)));// 必不可少！！！
        pwForUserOperationMenu.setOutsideTouchable(true);

        //给弹窗设置动画
        pwForUserOperationMenu.setAnimationStyle(R.style.BottomDialog);
        // 显示弹窗    屏幕密度乘以负数 往左偏移 反之向右
//        pwForUserOperationMenu.showAsDropDown(ivHomeDevice, (int) (10 * mDensity), 0);
        pwForUserOperationMenu.showAsDropDown(ivHomeDevice, 0, 0);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.getInstance(getActivity()).showMessage("选择 = " + deviceList.get(position).getDevice().getId());
                pwForUserOperationMenu.dismiss();
            }
        });
    }

    public void setDialog(final List<DeviceGet.DataBean> deviceList) {
        final Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.show();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.dialog_select_device, null);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rvDialogHome);
        rv.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
        HomeDeviceAdapter mAdapter = new HomeDeviceAdapter(dialog.getContext(), deviceList);
        rv.setAdapter(mAdapter);

        ImageView imageView = (ImageView) view.findViewById(R.id.ivDialogHome);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(display.getWidth(), display.getHeight());
        dialog.setContentView(view, layoutParams);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SPUtils.savePreference(getActivity(), "deviceid", deviceList.get(position).getDevice().getId());
                dialog.dismiss();
            }
        });

    }

    //定义接口变量的set方法
    public void setOnItemClickListener(OnItemClickListenerHeather onItemClickListenerHeather) {
        this.onItemClickListenerHeather = onItemClickListenerHeather;
    }

    private void initData() {
        //banner
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rpvHome.getLayoutParams();
        params.height = width / 3;//宽高比 1:3
        rpvHome.setLayoutParams(params);

        rpvHome.setAdapter(new ImageLoopAdapter(rpvHome, getContext()));
        rpvHome.setOnItemClickListener(new OnClickListeners() {
            @Override
            public void onItemClick(int position) {

            }
        });


    }

    public void getDevice() {
        String PATH_RSA = "uid=" + SPUtils.getPreference(getActivity(), "uid") + "&token=" + SPUtils.getPreference(getActivity(), "token");
        try {
            PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
            byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
            xUtilsGerDevice(Base64Utils.encode(encryptByte).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xUtilsGerDevice(String sign) {
        Map<String, Object> map = new HashMap<>();
        map.put("sign", sign);
        HttpxUtils.Post(getActivity(),
                HttpPath.DEVICE_GETDEVICE,
                map,
                new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("设备列表 = " + result);
                        DeviceGet deviceGet = GsonUtil.gsonIntance().gsonToBean(result, DeviceGet.class);
                        if (deviceGet.getStatus() == 1) {
//                            showUserOperationMenu(deviceGet.getData());
                            deviceList = deviceGet.getData();
                            for (int i = 0; i < deviceList.size(); i++) {
                                if (SPUtils.getPreference(getActivity(), "deviceid").equals(deviceList.get(i).getDevice().getId())) {
                                    //tvHomeDeviceName.setText(deviceList.get(i).getDevice().getDevice());
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    @Override
    public void onCallback(String type, String msg) {
        if (msg.equals("CALL")) {
            ToastUtils.getInstance(getActivity()).showMessage("通话请求发出");
        } else {
            ToastUtils.getInstance(getActivity()).showMessage("通话请求未发出");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 手机号验证
     *
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.ivHomeDevice, R.id.ivHome1, R.id.ivHome2, R.id.ivHome3, R.id.ivHome4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivHome1:
                //微聊
//                startActivity(new Intent(getActivity(), ChatingActivity.class));
                ToastUtils.getInstance(getActivity()).showMessage("暂未开放");
                break;
            case R.id.ivHome2:
                //电话本
                startActivity(new Intent(getActivity(), TelephoneActivity.class));
                break;
            case R.id.ivHome3:
                //通话
                final Dialog bottomDialog = new Dialog(getActivity(), R.style.BottomDialog);
                View views = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_call, null);
                bottomDialog.setContentView(views);
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) views.getLayoutParams();
                params.width = getActivity().getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(getActivity(), 100f);
                params.bottomMargin = DensityUtil.dp2px(getActivity(), 100f);
                views.setLayoutParams(params);
                bottomDialog.getWindow().setGravity(Gravity.CENTER);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();

                final EditText editText = (EditText) views.findViewById(R.id.editDialogCall);
                Button butNo = (Button) views.findViewById(R.id.butDialogCallNo);
                Button butYes = (Button) views.findViewById(R.id.butDialogCallYes);

                butYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                            if (isMobile(editText.getText().toString().trim())) {
                                //[DQHB*uid*LEN*CALL,device_id,号码]
                                //MyApplacation.tcpClient.send("[DQHB*" + uid + "*16*CALL," + deviceid + "," + editText.getText().toString().trim() + "]");
                                MyApplacation.tcpHelper.SendString("[DQHB*" + uid + "*16*CALL," + deviceid + "," + editText.getText().toString().trim() + "]");
                                bottomDialog.dismiss();
                            } else {
                                ToastUtils.getInstance(getActivity()).showMessage("请输入正确的电话号码");
                            }
                        } else {
                            ToastUtils.getInstance(getActivity()).showMessage("请输入电话号码");
                        }
                    }
                });

                butNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomDialog.dismiss();
                    }
                });
                break;
            case R.id.ivHome4:
                //吃药提醒
                //ToastUtils.getInstance(getActivity()).showMessage("暂未开放");
                startActivity(new Intent(getActivity(), MedicineActivity.class));
                break;
            case R.id.ivHomeDevice:
                //选择设备
                setDialog(deviceList);
                break;
        }
    }
}
