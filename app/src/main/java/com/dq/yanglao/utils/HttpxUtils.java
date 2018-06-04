package com.dq.yanglao.utils;


//package com.dq.yanglao.utils;
//import android.app.Activity;
//
//
//import com.dq.yanglao.view.MyProgressDialog;
//import com.google.gson.Gson;
//
//import org.xutils.common.Callback;
//import org.xutils.http.RequestParams;
//import org.xutils.http.app.ResponseParser;
//import org.xutils.http.request.UriRequest;
//import org.xutils.x;
//
//import java.lang.reflect.Type;
//import java.util.Map;
//
///**
// * @Title 封装xUtils网络请求
// * @Authour jingang
// * @Time 2016年7月27日 下午3:25:21
// */
//public class HttpxUtils {
//    private static MyProgressDialog myProgressDialog;
//
//    /**
//     * 发送get请求
//     *
//     * @param <T>
//     */
//    public static <T> Callback.Cancelable Get(Activity activity, String url, Map<String, String> map, final Callback.CommonCallback<String> callback) {
//        showDialog(activity);
//        RequestParams params = new RequestParams(url);
//        if (null != map) {
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                params.addQueryStringParameter(entry.getKey(), entry.getValue());
//            }
//        }
//        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                dismissDialog();
//                callback.onSuccess(result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                dismissDialog();
//                callback.onError(ex, isOnCallback);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                dismissDialog();
//                callback.onCancelled(cex);
//            }
//
//            @Override
//            public void onFinished() {
//                dismissDialog();
//                callback.onFinished();
//            }
//        });
//        return cancelable;
//    }
//
//    /**
//     * 发送post请求
//     *
//     * @param <T>
//     */
//    public static <T> Callback.Cancelable Post(Activity activity, String url, Map<String, Object> map, final Callback.CommonCallback<String> callback) {
//        showDialog(activity);
//        RequestParams params = new RequestParams(url);
//        if (null != map) {
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                params.addParameter(entry.getKey(), entry.getValue());
//            }
//        }
//        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                dismissDialog();
//                callback.onSuccess(result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                dismissDialog();
//                callback.onError(ex, isOnCallback);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                dismissDialog();
//                callback.onCancelled(cex);
//            }
//
//            @Override
//            public void onFinished() {
//                dismissDialog();
//                callback.onFinished();
//            }
//        });
//        return cancelable;
//    }
//
//    /**
//     * 上传文件
//     *
//     * @param <T>
//     */
//    public static <T> Callback.Cancelable UpLoadFile(Activity activity, String url, Map<String, Object> map, final Callback.CommonCallback<String> callback) {
//        showDialog(activity);
//        RequestParams params = new RequestParams(url);
//        if (null != map) {
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                params.addParameter(entry.getKey(), entry.getValue());
//            }
//        }
//        params.setMultipart(true);
//        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                dismissDialog();
//                callback.onSuccess(result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                dismissDialog();
//                callback.onError(ex, isOnCallback);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                dismissDialog();
//                callback.onCancelled(cex);
//            }
//
//            @Override
//            public void onFinished() {
//                dismissDialog();
//                callback.onFinished();
//            }
//        });
//        return cancelable;
//    }
//
//    /**
//     * 下载文件
//     *
//     * @param <T>
//     */
//    public static <T> Callback.Cancelable DownLoadFile(Activity activity, String url, String filepath, final Callback.CommonCallback<String> callback) {
//        showDialog(activity);
//        RequestParams params = new RequestParams(url);
//        // 设置断点续传
//        params.setAutoResume(true);
//        params.setSaveFilePath(filepath);
//        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                dismissDialog();
//                callback.onSuccess(result);
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                dismissDialog();
//                callback.onError(ex, isOnCallback);
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//                dismissDialog();
//                callback.onCancelled(cex);
//            }
//
//            @Override
//            public void onFinished() {
//                dismissDialog();
//                callback.onFinished();
//            }
//        });
//        return cancelable;
//    }
//
//    public class JsonResponseParser implements ResponseParser {
//        // 检查服务器返回的响应头信息
//        @Override
//        public void checkResponse(UriRequest request) throws Throwable {
//        }
//
//
//        /**
//         * 转换result为resultType类型的对象
//         *
//         * @param resultType  返回值类型(可能带有泛型信息)
//         * @param resultClass 返回值类型
//         * @param result      字符串数据
//         * @return
//         * @throws Throwable
//         */
//        @Override
//        public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
//            return new Gson().fromJson(result, resultClass);
//        }
//    }
//
//    /*开始dialog*/
//    public static void showDialog(Activity activity) {
//        if (myProgressDialog != null) {
//            myProgressDialog.show();
//        } else {
//            myProgressDialog = new MyProgressDialog(activity);
//            myProgressDialog.show();
//        }
//    }
//
//    /*结束dialog*/
//    public static void dismissDialog() {
//        if (myProgressDialog != null) {
//            myProgressDialog.dismiss();
//            myProgressDialog = null;
//        }
//    }
//}


import android.app.Activity;
import android.util.Log;

import com.dq.yanglao.view.MyProgressDialog;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title 封装xUtils网络请求
 * @Authour jingang
 * @Time 2016年7月27日 下午3:25:21
 */
public class HttpxUtils {
    //所有dialog的集合
    private static Map<Integer, MyProgressDialog> dialogMap = new HashMap<>();
    //记录dialog的key
    private static int dialogInt = 0;

    /**
     * 发送get请求
     *
     * @param <T>
     */
//    public static <T> Callback.Cancelable Get(String url, Map<String, String> map, final Callback.CommonCallback<String> callback) {
//        RequestParams params = new RequestParams(url);
//        if (null != map) {
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                params.addQueryStringParameter(entry.getKey(), entry.getValue());
//            }
//        }
//        Callback.Cancelable cancelable = x.http().get(params, callback);
//        return cancelable;
//    }
    public static <T> Callback.Cancelable Get(Activity activity, String url, Map<String, String> map, final Callback.CommonCallback<String> callback) {
        Log.d("Http==Get=", "url=" + url);
        Log.d("Http==Get=", "map=" + (map == null ? "" : map.toString()));
        showDialog(activity);
        final int nowDialog = dialogInt;
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dismissDialog(nowDialog);
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dismissDialog(nowDialog);
                callback.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                dismissDialog(nowDialog);
                callback.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                dismissDialog(nowDialog);
                callback.onFinished();
            }
        });
        return cancelable;
    }

    /**
     * 发送post请求
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable Post(Activity activity, String url, Map<String, Object> map, final Callback.CommonCallback<String> callback) {
        Log.d("Http==Post=", "url=" + url);
        Log.d("Http==Post=", "map=" + (map == null ? "" : map.toString()));
        showDialog(activity);
        final int nowDialog = dialogInt;
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Callback.Cancelable cancelable = x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dismissDialog(nowDialog);
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dismissDialog(nowDialog);
                callback.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                dismissDialog(nowDialog);
                callback.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                dismissDialog(nowDialog);
                callback.onFinished();
            }
        });
        return cancelable;
    }

    /**
     * 上传文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable UpLoadFile(Activity activity, String url, Map<String, Object> map, final Callback.CommonCallback<String> callback) {
        showDialog(activity);
        final int nowDialog = dialogInt;
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        params.setMultipart(true);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dismissDialog(nowDialog);
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dismissDialog(nowDialog);
                callback.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                dismissDialog(nowDialog);
                callback.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                dismissDialog(nowDialog);
                callback.onFinished();
            }
        });
        return cancelable;
    }

    /**
     * 下载文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable DownLoadFile(Activity activity, String url, String filepath, final Callback.CommonCallback<String> callback) {
        showDialog(activity);
        final int nowDialog = dialogInt;
        RequestParams params = new RequestParams(url);
// 设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filepath);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                dismissDialog(nowDialog);
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                dismissDialog(nowDialog);
                callback.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                dismissDialog(nowDialog);
                callback.onCancelled(cex);
            }

            @Override
            public void onFinished() {
                dismissDialog(nowDialog);
                callback.onFinished();
            }
        });
        return cancelable;
    }

    public class JsonResponseParser implements ResponseParser {
        // 检查服务器返回的响应头信息
        @Override
        public void checkResponse(UriRequest request) throws Throwable {
        }


        /**
         * 转换result为resultType类型的对象
         *
         * @param resultType  返回值类型(可能带有泛型信息)
         * @param resultClass 返回值类型
         * @param result      字符串数据
         * @return
         * @throws Throwable
         */
        @Override
        public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
            return new Gson().fromJson(result, resultClass);
        }
    }

    /*开始dialog*/
    public static void showDialog(Activity activity) {
        ++dialogInt;
        try {
            MyProgressDialog myProgressDialog = new MyProgressDialog(activity);
            myProgressDialog.show();
            dialogMap.put(dialogInt, myProgressDialog);
        } catch (Exception e) {
        }
    }

    /*结束dialog*/
    public static void dismissDialog(int a) {
        try {
            dialogMap.get(a).dismiss();
            dialogMap.remove(dialogMap.get(a));
        } catch (Exception e) {
        }
    }
}