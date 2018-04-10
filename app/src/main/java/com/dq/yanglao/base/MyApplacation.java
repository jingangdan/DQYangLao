package com.dq.yanglao.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.view.WindowManager;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @describe：程序Application
 * @author：jingang
 * @createdate：2018/03/22
 */
public class MyApplacation extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        //百度地图
//        SDKInitializer.initialize(getApplicationContext());
//        //极光推送
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
//        //优酷视频
//        YoukuPlayerConfig.setClientIdAndSecret("25b1adbc8bff2e78","f71e3e65611e496f65675f976beee3c1");
//        YoukuPlayerConfig.onInitial(this);
//        YoukuPlayerConfig.setLog(false);
//
//        //本地数据保存
//        PublicStaticData.sharedPreferences = getSharedPreferences(SP_FILE, MODE_PRIVATE);
//        //手机文件保存路径
//        PublicStaticData.isSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//        if (PublicStaticData.isSDCard) {
//            PublicStaticData.outDir = Environment.getExternalStorageDirectory().toString();
//        } else {
//            PublicStaticData.outDir = Environment.getDataDirectory().toString();
//        }
//        //图片保存路径
//        PublicStaticData.picFilePath = PublicStaticData.outDir + "/diaoyu/picture/";
//
//        //屏幕参数
//        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
//        int height = wm.getDefaultDisplay().getHeight();
//        PublicStaticData.width = width;
//        PublicStaticData.height = height;
//
//        /**
//         * OkHttp
//         */
//        //持久化cookie 保存在SharedPreferences中
//        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .addInterceptor(new LoggerInterceptor("OkHttpLogTAG"))
//                .cookieJar(cookieJar)
//                .build();
//        OkHttpUtils.initClient(okHttpClient);
//        PlatformConfig.setWeixin("wx616d1fd5400a1814", "9da56992ceba7e67392f9953a59a55e8");
//        PlatformConfig.setQQZone("1105975213", "8EntYiXzl20pctfI");
//        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
//        UMShareAPI.get(this);
//
//        //二维码扫描
//        ZXingLibrary.initDisplayOpinion(this);
    }

    //验证各种导航地图是否安装
//    public static boolean isAvilible(Context context, String packageName) {
//        //获取packagemanager
//        final PackageManager packageManager = context.getPackageManager();
//        //获取所有已安装程序的包信息
//        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
//        //用于存储所有已安装程序的包名
//        List<String> packageNames = new ArrayList<String>();
//        //从pinfo中将包名字逐一取出，压入pName list中
//        if (packageInfos != null) {
//            for (int i = 0; i < packageInfos.size(); i++) {
//                String packName = packageInfos.get(i).packageName;
//                packageNames.add(packName);
//            }
//        }
//        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
//        return packageNames.contains(packageName);
//    }

}
