package com.dq.yanglao.base;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.widget.Toast;

import com.dq.yanglao.utils.SPUtils;
import com.dq.yanglao.utils.ToastUtils;
import com.dq.yanglao.view.TcpClient;
import com.dq.yanglao.view.TcpHelper;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.rong.imkit.RongIM;

/**
 * @describe：程序Application
 * @author：jingang
 * @createdate：2018/03/22
 */
public class MyApplacation extends MultiDexApplication {
    public static Context context;
//    public static TcpClient tcpClient;
//    public static ExecutorService exec;

//    private final MyHandler myHandler = new MyHandler(this);
//    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();


    //
    public static TcpHelper tcpHelper;
    private String TcpRecData;
    private MyHandler handler;
    public static TcpReceive tcpReceive;

    @Override
    public void onCreate() {
        super.onCreate();

        // 多进程导致多次初始化Application,这里只初始化App主进程的Application
        String curProcessName = getCurProcessName(this);
        if (!curProcessName.equals(getPackageName())) {
            return;
        }
        context = this;
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }

        /**
         *
         */
        if (SPUtils.getPreference(this, "isLogin").equals("1")) {
            startTCP();
        }

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

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public void startTCP() {
        if (tcpHelper == null) {
            tcpHelper = new TcpHelper("47.52.199.154", 49152, this);
            tcpReceive = new TcpReceive();
            tcpHelper.setReceiveEvent(tcpReceive);
            handler = new MyHandler();

        }
    }

    public class TcpReceive implements TcpHelper.OnReceiveEvent {
        public synchronized void ReceiveBytes(byte[] iData) {
        }

        public synchronized void ReceiveString(String iData) {
            TcpRecData = iData;
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
        }
    }

    class MyHandler extends Handler {
        public MyHandler() {
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // butTest.append(TcpRecData);  //接收到数据显示到TextView上
//                    Toast.makeText(MyApplacation.this, "MyApplication接收 = " + msg.obj.toString(), Toast.LENGTH_LONG).show();
//                    System.out.println("MyApplication接收 = " + msg.obj.toString());

                    ToastUtils.getInstance(context).showMessage("MyApplication接收 =" + TcpRecData);
                    System.out.println("MyApplication接收 = " + TcpRecData);

                    if (!TextUtils.isEmpty(TcpRecData)) {
                        setReceicer(TcpRecData);
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    }

//    public void startTCP() {
//        bindReceiver();
//        if (tcpClient == null) {
//            exec = Executors.newCachedThreadPool();
//            tcpClient = new TcpClient("47.52.199.154", 49152, this);
//            exec.execute(tcpClient);
//        }
//    }

//    private class MyBroadcastReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String mAction = intent.getAction();
//            switch (mAction) {
//                case "tcpClientReceiver":
//                    String msg = intent.getStringExtra("tcpClientReceiver");
//                    Message message = Message.obtain();
//                    message.what = 1;
//                    message.obj = msg;
//                    myHandler.sendMessage(message);
//                    break;
//            }
//        }
//    }

//    private class MyHandler extends android.os.Handler {
//        private WeakReference<MyApplacation> mActivity;
//
//        MyHandler(MyApplacation activity) {
//            mActivity = new WeakReference<MyApplacation>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            if (mActivity != null) {
//                switch (msg.what) {
//                    case 1:
//                        Toast.makeText(MyApplacation.this, "MyApplication接收 = " + msg.obj.toString(), Toast.LENGTH_LONG).show();
//                        System.out.println("MyApplication接收 = " + msg.obj.toString());
//
//                        if (!TextUtils.isEmpty(msg.obj.toString())) {
//                            setReceicer(msg.obj.toString());
//                        }
//                        break;
//                    case 2:
//                        System.out.println("MyApplication发送 = " + msg.obj.toString());
//                        break;
//                }
//            }
//        }
//    }

//    private void bindReceiver() {
//        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
//        registerReceiver(myBroadcastReceiver, intentFilter);
//    }

    public void setReceicer(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent("com.example.broadcast.FORCE_EXIT");
                intent.putExtra("msg", msg);
                sendBroadcast(intent);
            }
        }).start();
    }

}
