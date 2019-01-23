package com.test.reviewandroid.okhttp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.test.reviewandroid.R;
import com.test.reviewandroid.okhttp.httpurlconnention.Httpurlconnention;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {
    private static final String TAG = "OkHttpActivity";
    public String url = "http://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        ButterKnife.bind(this);
    }

    /**
     * @createTime: 2019/1/21
     * @author lady_zhou
     * @Description okhttp同步请求
     */
    public void okHttpGet() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象

                    Request request = new Request.Builder()
                            .url(url)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象

                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象

                    if (response.isSuccessful()) {
                        Log.d(TAG, "response.code()==" + response.code());
                        Log.d(TAG, "response.message()==" + response.message());
                        Log.d(TAG, "res==" + response.body().string());
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * @createTime: 2019/1/21
     * @author lady_zhou
     * @Description okhttp异步请求
     */
    public void okHttpSysGet() {
        OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象

        Request request = new Request.Builder()
                .url(url)//请求接口。如果需要传参拼接到接口后面。
                .get()//默认是get请求，可以不写
                .build();//创建Request 对象

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "方法：onFailure: 失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "方法：onResponse: " + response.body().string());
            }
        });
    }

    @OnClick({R.id.bt_get, R.id.bt_post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get:
//                okHttpGet();
//                okHttpSysGet();
                ConnectTask task = new ConnectTask();
                task.execute();
                break;
            case R.id.bt_post:
                break;
        }
    }

    private class ConnectTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Httpurlconnention.httpGet();//调用Httpurlconnention
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //这里更新ui
            Toast.makeText(OkHttpActivity.this, "请求成功了", Toast.LENGTH_SHORT).show();
        }
    }
}
