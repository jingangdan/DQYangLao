package com.dq.yanglao.utils;

/**
 * 接口地址
 * Created by jingang on 2018/4/24.
 */

public class HttpPath {
    //public static final String PATH = "http://192.168.0.128/";//接口地址

    public static final String PATH = "http://yanglao.dequanhuibao.com/";//接口地址

    public static final String USER_LOGIN = PATH + "Api/User/login?";//用户登录(phone,pwd)

    public static final String USER_RES = PATH + "Api/User/register?";//用户注册(mobile,pwd,verify验证码)

    public static final String USER_HASMOBILE = PATH + "Api/User/hasmobile?";//验证手机号是否已经注册（mobile）

    public static final String USER_SENDSMS = PATH + "Api/User/sendsms?";//发送短信-验证码（mobile，type类型—-注册(register)—找回密码(repwd)）

    public static final String USER_REPWD = PATH + "Api/User/repwd?";//找回密码(mobile,pwd,verify验证码)

    public static final String DEVICE_BIND = PATH + "Api/Device/bind?";//绑定设备（device，uid, token）

    public static final String DEVICE_UNBIND = PATH + "Api/Device/unbind?";//解绑设备（device，uid, token）

    public static final String DEVICE_GETDEVICE = PATH + "Api/Device/getdevice?";//获取用户所有绑定的设备(uid, token)

    public static final String DEVICE_INFO = PATH + "Api/Device/info?";//获取用户绑定设备或者申请授权(id,uid,token)

    public static final String DEVICE_GETSOS = PATH + "Api/Device/getsos?";//获取设备sos号码(device_id,uid,token)

    public static final String DEVICE_GETPHB = PATH + "Api/Device/getphb?";//获取设备通讯录(device_id,uid,token)

    public static final String DEVICE_HEART = PATH + "Api/Device/getlastheart?";//获取上次检测心率(device_id,uid,token)

    public static final String DEVICE_SETTING = PATH + "Api/Device/getsetting?";//获取设备设置（uid, token, device_id）

    public static final String DEVICE_STEP = PATH + "Api/Device/getstep?";//根据时间获取步数(uid, token, device_id,date)

    public static final String DEVICE_GETFENCE = PATH + "Api/Device/getfence?";//获取设备围栏（uid, token, device_id）

    public static final String DEVICE_SETFENCE = PATH + "Api/Device/setfence?";//设置围栏（uid, token, device_id，lat,lng,redii）

    public static final String DEVICE_GETLIST = PATH + "Api/Device/getlist?";//获取用户所有绑定用户（uid, token, device_id）

    public static final String DEVICE_GETDINFO = PATH + "Api/Device/getdinfo?";//获取设备老人信息（uid, token, device_id）

    public static final String DEVICE_SETDINFO = PATH + "Api/Device/setdinfo?";//设置老人信息（uid, token, device_id）

    public static final String DEVICE_UPIMG = PATH + "Api/Device/upimg?";//上传图片（uid, token, $_FILE[‘image’]）

    public static final String DEVICE_GETUINFO = PATH + "Api/Device/getuinfo?";//获取用户信息（uid, token）

}
