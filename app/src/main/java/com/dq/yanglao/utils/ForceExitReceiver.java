package com.dq.yanglao.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dq.yanglao.Interface.OnCallBackTCP;
import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.bean.DeviceInfo;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.security.PrivateKey;

/**
 * Created by jingang on 2018/4/26.
 */

public class ForceExitReceiver extends BroadcastReceiver {
    private String msg = "";
    String[] temp = null;
    String[] temp2 = null;
    String string, string2;
    private static OnCallBackTCP mCallback;

    @Override
    public void onReceive(final Context context, Intent intent) {
        msg = intent.getStringExtra("msg");

        string = msg.substring(1, msg.indexOf("]"));//去掉前后字符（中括号）

        string2 = string.substring(string.indexOf(",") + 1);//获取第一个“，”之后的字符串

        temp = string.split(",");//以逗号拆分

        temp2 = temp[0].toString().split("\\*");

        if (!TextUtils.isEmpty(temp2[3])) {
            setTCP(temp2[3]);
        }

        System.out.println("555555555555 = " + temp2[3].toString());

        System.out.println("55555 = " + intent.getStringExtra("msg"));

        System.out.println("string2 = " + string2);

    }

    /**
     * @param type
     */
    private void setTCP(String type) {
        switch (type) {
            case "NOSOCKET":
                //服务未开启
                System.out.println("111服务未开启");
                break;
            case "APPLY":
                //用户申请授权
                //[DQHB*2*000A*APPLY,94,1]
//                mCallback.onCallback(temp[2]);
                mCallback.onCallback(type, string2);
                break;
            case "AUTH":
                //主账号授权
                String PATH_RSA = "id=" + temp[1] + "&uid=" + SPUtils.getPreference(MyApplacation.context, "uid") + "&token=" + SPUtils.getPreference(MyApplacation.context, "token");
                System.out.println("AUTH = " + PATH_RSA);
                try {
                    PrivateKey privateKey = RSAUtils.loadPrivateKey(RSAUtils.PRIVATE_KEY);
                    byte[] encryptByte = RSAUtils.encryptDataPrivate(PATH_RSA.getBytes(), privateKey);
                    xUtilsInfo(Base64Utils.encode(encryptByte).toString(), temp[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "CONN":
                //连接后发送首次连接标记(无需操作)
                break;
            case "CHECK":
                //保持链路（心跳）
                //MyApplacation.tcpClient.send(msg);
                MyApplacation.tcpHelper.SendString(msg);
                break;
            //设置SOS号码
            case "SOS1":
//                mCallback.onCallbackType(temp2[3]);
                mCallback.onCallback(type, string2);
                break;
            case "SOS2":
//                mCallback.onCallbackType(temp2[3]);
                mCallback.onCallback(type, string2);
                break;
            case "SOS3":
//                mCallback.onCallbackType(temp2[3]);
                mCallback.onCallback(type, string2);
                break;
            case "PHB":
                //设置通讯录/电话本
//                mCallback.onCallbackType(type);
                mCallback.onCallback(type, string2);
                break;

            case "CALL":
                //打电话
//                mCallback.onCallbackType(type);
                mCallback.onCallback(type, string2);
                break;
            case "hrtstart":
                //心率状态--[DQHB*1*000A*hrtstart,5]
//                mCallback.onCallbackId(type,temp[1]);
                mCallback.onCallback(type, string2);
                break;
            case "heart":
                //心率结果--[DQHB*1*001D*heart,5,0,2018-04-29 11:09:16]
//                mCallback.onCallBackHeart(type,temp[1],temp[2],temp[3]);
                mCallback.onCallback(type, string2);
                break;

            case "UD":
                //定位 [DQHB*1*001A*UD,5,35.065287,118.3212733]
                mCallback.onCallback(type, string2);
                break;
        }
    }

    /*回调方法*/
    public static void setOnClickListenerSOS(OnCallBackTCP onClickListenerSOS) {
        mCallback = onClickListenerSOS;
    }

    /**
     * 授权
     *
     * @param sign
     */
    public void xUtilsInfo(String sign, final String id) {
        System.out.println("授权 = " + HttpPath.DEVICE_INFO + "sign=" + sign);
        RequestParams params = new RequestParams(HttpPath.DEVICE_INFO);
        params.addBodyParameter("sign", sign);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("授权 = " + result);
                DeviceInfo deviceInfo = GsonUtil.gsonIntance().gsonToBean(result, DeviceInfo.class);
                if (deviceInfo.getStatus() == 1) {
                    setDialog(deviceInfo.getData().getDevice_name(),
                            deviceInfo.getData().getUinfo().getMobile(),
                            deviceInfo.getData().getUinfo().getName(),
                            deviceInfo.getData().getRelation(),
                            deviceInfo.getData().getUinfo().getHeadimg(),
                            id);
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

    /**
     *
     */
    private TextView tvDeviceName, tvMobile, tvName, tvRelation;
    private ImageView ivHeading;
    private Button butNo, butYes;

    public void setDialog(String deviceName, String mobile, String name, String relation, String heading, final String id) {
        final Activity activity = ScreenManagerUtils.getmCurrentActivity();
        final Dialog bottomDialog = new Dialog(activity, R.style.BottomDialog);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_shouquan, null);
        bottomDialog.setContentView(view);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.width = activity.getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(activity, 60f);
        params.bottomMargin = DensityUtil.dp2px(activity, 60f);
        view.setLayoutParams(params);
        bottomDialog.getWindow().setGravity(Gravity.CENTER);

        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        tvDeviceName = (TextView) view.findViewById(R.id.tvDialogDeviceName);
        tvMobile = (TextView) view.findViewById(R.id.tvDialogMobile);
        tvName = (TextView) view.findViewById(R.id.tvDialogName);
        tvRelation = (TextView) view.findViewById(R.id.tvDialogRelation);

        ivHeading = (ImageView) view.findViewById(R.id.ivDialogHeading);

        butNo = (Button) view.findViewById(R.id.butDialogNo);
        butYes = (Button) view.findViewById(R.id.butDialogYes);

        tvDeviceName.setText("申请授权设备：" + deviceName);
        tvMobile.setText(mobile);
        tvName.setText("昵称：" + name);
        tvRelation.setText("关系：" + relation);

        ImageUtils.loadIntoUseFitWidth(MyApplacation.context,
                heading,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                ivHeading);

        butNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.getInstance(MyApplacation.context).showMessage("拒绝");
                //[DQHB*用户id*LEN*AUTH,id,状态]
//                MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(MyApplacation.context, "uid") + "*16*AUTH," + id + ",0]");
                MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(MyApplacation.context, "uid") + "*16*AUTH," + id + ",0]");
                bottomDialog.dismiss();
            }
        });

        butYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.getInstance(MyApplacation.context).showMessage("同意");
//                MyApplacation.tcpClient.send("[DQHB*" + SPUtils.getPreference(MyApplacation.context, "uid") + "*16*AUTH," + id + ",1]");
                MyApplacation.tcpHelper.SendString("[DQHB*" + SPUtils.getPreference(MyApplacation.context, "uid") + "*16*AUTH," + id + ",1]");
                bottomDialog.dismiss();
            }
        });

    }

}
