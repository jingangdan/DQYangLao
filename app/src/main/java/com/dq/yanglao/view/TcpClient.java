package com.dq.yanglao.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.utils.SPUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * TCPSocket
 * Created by jingang on 2017-04-25.
 */
public class TcpClient implements Runnable {
    private String TAG = "TcpClient";
    private String serverIP = "192.168.0.128";
    private int serverPort = 49152;
    private PrintWriter pw;
    private InputStream is;
    private DataInputStream dis;
    private boolean isRun = true;
    private Socket socket = null;
    byte buff[] = new byte[4096];
    private String rcvMsg;
    private int rcvLen;

    private Context context;

    public TcpClient(String ip, int port, Context context) {
        this.context = context;
        this.serverIP = ip;
        this.serverPort = port;
    }

    public void closeSelf() {
        isRun = false;
    }

    public void send(String msg) {
        if (pw != null) {
            pw.println(msg);
            pw.flush();
        }

    }

    @Override
    public void run() {
        try {
            socket = new Socket(serverIP, serverPort);
            //socket.setSoTimeout(5000);
            System.out.println("连接情况 = " + socket.isConnected());
            pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("[DQHB*" + SPUtils.getPreference(context, "uid") + "*16" + "*CONN]");
            is = socket.getInputStream();
            dis = new DataInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (isRun) {
            try {
                if (dis != null) {
//                    while ((rcvLen = dis.read(buff)) != -1) {
//                        rcvLen = dis.read(buff);
//                        rcvMsg = new String(buff, 0, rcvLen, "utf-8");
//                        Log.i(TAG, "run: 收到消息:" + rcvMsg);
//                        Intent intent = new Intent();
//                        intent.setAction("tcpClientReceiver");
//                        intent.putExtra("tcpClientReceiver", rcvMsg);
//                        MyApplacation.context.sendBroadcast(intent);//将消息发送给主界面
//                        if (rcvMsg.equals("QuitClient")) {   //服务器要求客户端结束
//                            isRun = false;
//                        }
//                    }
                    rcvLen = dis.read(buff);
                    rcvMsg = new String(buff, 0, rcvLen, "utf-8");
                    Log.i(TAG, "run: 收到消息:" + rcvMsg);
                    Intent intent = new Intent();
                    intent.setAction("tcpClientReceiver");
                    intent.putExtra("tcpClientReceiver", rcvMsg);
                    MyApplacation.context.sendBroadcast(intent);//将消息发送给主界面
                    if (rcvMsg.equals("QuitClient")) {   //服务器要求客户端结束
                        isRun = false;
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            pw.close();
            is.close();
            dis.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
