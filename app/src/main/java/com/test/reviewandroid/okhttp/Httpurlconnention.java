package com.test.reviewandroid.okhttp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author lady_zhou
 * @createTime: 2019/1/23
 * @Description httpGet 使用方法
 */
public class Httpurlconnention {
    private static final String TAG = "httpGet";

    public static String httpGet() throws IOException {
        InputStream in = null;
        String result; //收到的返回结果
        String urlAdress = "http://www.baidu.com";

        try {
            URL url = new URL(urlAdress);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置读取超时为5秒
            httpURLConnection.setConnectTimeout(5000);
            //设置连接超时为5秒
            httpURLConnection.setReadTimeout(5000);
            //设置Post请求方式
            httpURLConnection.setRequestMethod("GET");
            //接受收入流
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            //建立连接，发起请求
            httpURLConnection.connect();
            in = httpURLConnection.getInputStream();
            //读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\r\n");//添加了个回车换行  方便控制台观看
            }
            result = response.toString();
            Log.d(TAG, "方法：httpGet: " + result);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return result;
    }

}
