package com.dq.yanglao.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.Interface.OnClickListeners;
import com.dq.yanglao.Interface.OnItemClickListener;
import com.dq.yanglao.Interface.OnItemClickListenerHeather;
import com.dq.yanglao.R;
import com.dq.yanglao.base.BaseRecyclerViewHolder;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseFragment;
import com.dq.yanglao.ui.ChatingActivity;
import com.dq.yanglao.ui.EquipmentActivity;
import com.dq.yanglao.ui.TelephoneActivity;
import com.dq.yanglao.utils.DensityUtil;
import com.dq.yanglao.utils.ForceExitReceiver;
import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ToastUtils;
import com.dq.yanglao.view.rollpagerview.ImageLoopAdapter;
import com.dq.yanglao.view.rollpagerview.RollPagerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页
 * Created by jingang on 2018/4/12.
 */
public class HomeFragment extends MyBaseFragment implements OnCallBackTCP {
    @Bind(R.id.rollPagerView)
    RollPagerView rollPagerView;
    @Bind(R.id.rvHomeMenu)
    RecyclerView rvHomeMenu;
    @Bind(R.id.ll_home_location)
    LinearLayout llHomeLocation;
    @Bind(R.id.rvHomeHealthy)
    RecyclerView rvHomeHealthy;
    @Bind(R.id.ivHomeHeader)
    ImageView ivHomeHeader;

    private OnItemClickListenerHeather onItemClickListenerHeather;//2、定义接口成员变量
    private RVAdapter1 rVAdapter1;
    private RVAdapter2 rVAdapter2;
    private String uid, deviceid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_home, null);
        ButterKnife.bind(this, view);
        uid = SPUtils.getPreference(getActivity(), "uid");
        deviceid = SPUtils.getPreference(getActivity(), "deviceid");

        initData();

        ForceExitReceiver.setOnClickListenerSOS(this);

        return view;
    }

    //定义接口变量的set方法
    public void setOnItemClickListener(OnItemClickListenerHeather onItemClickListenerHeather) {
        this.onItemClickListenerHeather = onItemClickListenerHeather;
    }

    private void initData() {
        //banner
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rollPagerView.getLayoutParams();
        params.height = width / 3;//宽高比 1:3
        rollPagerView.setLayoutParams(params);

        rollPagerView.setAdapter(new ImageLoopAdapter(rollPagerView, getContext()));
        rollPagerView.setOnItemClickListener(new OnClickListeners() {
            @Override
            public void onItemClick(int position) {

            }
        });

        //上方四个menu
        rVAdapter1 = new RVAdapter1(new ArrayList<Integer>() {{
            add(R.mipmap.ic_home_menu001);
            add(R.mipmap.ic_home_menu002);
            add(R.mipmap.ic_home_menu003);
            add(R.mipmap.ic_home_menu004);
        }}, getActivity(), 0);
        rvHomeMenu.setLayoutManager(new GridLayoutManager(getActivity(), 4, GridLayoutManager.VERTICAL, false));
        rvHomeMenu.setAdapter(rVAdapter1);
        rVAdapter1.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        //微聊
                        startActivity(new Intent(getActivity(), ChatingActivity.class));
                        break;
                    case 1:
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
                    case 2:
                        //电话本
                        startActivity(new Intent(getActivity(), TelephoneActivity.class));
                        break;
                    case 3:
                        //吃药提醒
                        break;
                }
            }
        });

        //下方三个 计步 心率 睡眠
        rVAdapter2 = new RVAdapter2(new ArrayList<Integer>() {{
            add(R.mipmap.ic_home_healthy001);
            add(R.mipmap.ic_home_healthy002);
            add(R.mipmap.ic_home_healthy003);
        }}, getActivity(), 0);
        rvHomeHealthy.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHomeHealthy.setAdapter(rVAdapter2);
        rVAdapter2.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        //计步
                        if (onItemClickListenerHeather != null) {
                            onItemClickListenerHeather.onItemClick(view, 1, 0);
                        }
                        break;
                    case 1:
                        //心率
                        if (onItemClickListenerHeather != null) {
                            onItemClickListenerHeather.onItemClick(view, 1, 1);
                        }
                        break;
                    case 2:
                        //睡眠
                        if (onItemClickListenerHeather != null) {
                            onItemClickListenerHeather.onItemClick(view, 1, 2);
                        }
                        break;
                }
            }
        });

    }

    @OnClick({R.id.ll_home_location, R.id.ivHomeHeader})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivHomeHeader:
                //
                startActivity(new Intent(getActivity(), EquipmentActivity.class));
                break;
            case R.id.ll_home_location:
                if (onItemClickListenerHeather != null) {
                    onItemClickListenerHeather.onItemClick(view, 2, 0);
                }
                break;
        }

    }

    @Override
    public void onCallback(String type, String msg) {
        if (msg.equals("CALL")) {
            ToastUtils.getInstance(getActivity()).showMessage("通话请求发出");
        } else {
            ToastUtils.getInstance(getActivity()).showMessage("通话请求未发出");
        }
    }

    private class RVAdapter1 extends RecyclerView.Adapter<RVAdapter1.MyViewHolder> {
        private Context mContext;
        private List<Integer> list;
        private OnItemClickListener onItemClickListener;

        public RVAdapter1(List<Integer> list, Context mContext, int resid) {
            this.mContext = mContext;
            this.list = list;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hone_menu, parent, false));
            return vh;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }


            holder.img.setImageResource(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends BaseRecyclerViewHolder {
            private ImageView img;

            public MyViewHolder(View view) {
                super(view);
                img = (ImageView) view.findViewById(R.id.item_iv_menu);
            }
        }
    }

    private class RVAdapter2 extends RecyclerView.Adapter<RVAdapter2.MyViewHolder> {
        private Context mContext;
        private List<Integer> list;
        private OnItemClickListener onItemClickListener;

        public RVAdapter2(List<Integer> list, Context mContext, int resid) {
            this.mContext = mContext;
            this.list = list;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder vh = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hone_menu2, parent, false));
            return vh;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });
            }

            holder.img.setImageResource(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends BaseRecyclerViewHolder {
            private ImageView img;
            private TextView textView;

            public MyViewHolder(View view) {
                super(view);
                img = (ImageView) view.findViewById(R.id.item_iv_menu2);
                textView = (TextView) view.findViewById(R.id.item_tv_menu2);
            }
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
}
