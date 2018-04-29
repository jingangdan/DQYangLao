package com.dq.yanglao.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dq.yanglao.R;
import com.dq.yanglao.base.MyApplacation;
import com.dq.yanglao.base.MyBaseActivity;
import com.dq.yanglao.view.TcpHelper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by asus on 2018/4/27.
 */

public class TestActivity extends MyBaseActivity {
    @Bind(R.id.editTest)
    EditText editTest;
    @Bind(R.id.butTest)
    Button butTest;
    @Bind(R.id.tvTest)
    TextView tvTest;

    private TcpHelper tcpHelper;
    private String SerIp;
    private int SerPort;
    private String TcpRecData;
    private MyHandler handler;
    private TcpReceive tcpReceive;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.butTest)
    public void onViewClicked() {
        if (!TextUtils.isEmpty(editTest.getText().toString().trim())) {
//            MyApplacation.tcpClient.send(URLEncoder.encode(editTest.getText().toString().trim()));
            MyApplacation.tcpHelper.SendString(URLEncoder.encode(editTest.getText().toString().trim()));

            tvTest.setText(URLEncoder.encode(editTest.getText().toString().trim()));

        } else {
            showMessage("输入为空");
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
                    butTest.append(TcpRecData);  //接收到数据显示到TextView上
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public void onConnectClick(View view) {
        if (tcpHelper == null) {

//            SerIp = ((EditText) findViewById(R.id.EdtTcpSerIp)).getText().toString();
//            SerPort = Integer.parseInt(((TextView) findViewById(R.id.EdtTcpPort)).getText().toString());

            tcpHelper = new TcpHelper("47.52.199.154", 49152,this);
            tcpReceive = new TcpReceive();
            tcpHelper.setReceiveEvent(tcpReceive);
            handler = new MyHandler();

        }
    }

    public void onSendMsgClick(View view) {
        try {
            String iText = editTest.getText().toString();
            //发送
            tcpHelper.SendString(iText);
        } catch (Exception e) {

        }
    }

}
