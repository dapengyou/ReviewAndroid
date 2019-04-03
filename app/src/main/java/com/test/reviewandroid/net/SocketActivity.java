package com.test.reviewandroid.net;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.test.reviewandroid.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SocketActivity extends AppCompatActivity {
    @BindView(R.id.et_text)
    EditText mEtText;
    @BindView(R.id.tv_text)
    TextView mTvText;

    private static final String TAG = "TAG";
    private static final String HOST = "192.168.110.112";
    private static final int PORT = 9999;
    private PrintWriter printWriter;
    private BufferedReader in;
    private ExecutorService mExecutorService = null;
    private String receiveMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        ButterKnife.bind(this);

        mExecutorService = Executors.newCachedThreadPool();
    }

    public void connect() {
        mExecutorService.execute(new connectService());
    }

    public void send() {
        String sendMsg = mEtText.getText().toString();
        mExecutorService.execute(new sendService(sendMsg));
    }

    public void disconnect() {
        mExecutorService.execute(new sendService("0"));
    }

    @OnClick({R.id.bt_connect, R.id.bt_send, R.id.bt_disconnect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_connect:
                connect();
                break;
            case R.id.bt_send:
                send();
                break;
            case R.id.bt_disconnect:
                disconnect();
                break;
        }
    }

    private class sendService implements Runnable {
        private String msg;

        sendService(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            printWriter.println(this.msg);
        }
    }

    private class connectService implements Runnable {
        @Override
        public void run() {
            try {
                Socket socket = new Socket(HOST, PORT);
                socket.setSoTimeout(60000);
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        socket.getOutputStream(), "UTF-8")), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                receiveMsg();
            } catch (Exception e) {
                Log.e(TAG, ("connectService:" + e.getMessage()));
            }
        }
    }

    private void receiveMsg() {
        try {
            while (true) {
                if ((receiveMsg = in.readLine()) != null) {
                    Log.d(TAG, "receiveMsg:" + receiveMsg);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                             mTvText.setText(receiveMsg + "\n\n" + mTvText.getText());
                        }
                    });
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "receiveMsg: ");
            e.printStackTrace();
        }
    }

}
