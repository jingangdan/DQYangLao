package com.dq.yanglao.utils;

/**
 * 接口地址
 * Created by jingang on 2018/4/24.
 */

public class HttpPath {
    public static final String PATH = "http://192.168.0.128/";//接口地址

    public static final String USER_LOGIN = PATH + "Api/User/login?";//用户登录(phone,pwd)

    public static final String USER_RES = PATH + "Api/User/register?";//用户注册(mobile,pwd,verify验证码)

    public static final String USER_HASMOBILE = PATH + "Api/User/hasmobile?";//验证手机号是否已经注册（mobile）

    public static final String USER_SENDSMS = PATH + "Api/User/sendsms?";//发送短信-验证码（mobile，type类型—-注册(register)—找回密码(repwd)）

    public static final String DEVICE_BIND = PATH + "Api/Device/bind?";//绑定设备（device，uid, token）

    public static final String DEVICE_GETDEVICE = PATH + "Api/Device/getdevice?";//获取用户所有绑定的设备(device, uid, token)

    public static final String DEVICE_INFO = PATH + "Api/Device/info?";//获取用户绑定设备或者申请授权(id,uid,token)

}
