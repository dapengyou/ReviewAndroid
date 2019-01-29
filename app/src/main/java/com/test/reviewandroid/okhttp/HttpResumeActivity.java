package com.test.reviewandroid.okhttp;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.test.reviewandroid.R;
import com.test.reviewandroid.okhttp.httpResume.DownLoadUtil;
import com.test.reviewandroid.okhttp.httpResume.DownloadListener;
import com.test.reviewandroid.okhttp.httpResume.Util;

import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HttpResumeActivity extends AppCompatActivity {
    private static final String TAG = "HttpResumeActivity";

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.size)
    TextView mSize;
    @BindView(R.id.start)
    Button mStart;
    @BindView(R.id.stop)
    Button mStop;
    @BindView(R.id.cancel)
    Button mCancel;

    private static final int DOWNLOAD_PRE = 0x01;
    private static final int DOWNLOAD_STOP = 0x02;
    private static final int DOWNLOAD_FAILE = 0x03;
    private static final int DOWNLOAD_CANCEL = 0x04;
    private static final int DOWNLOAD_RESUME = 0x05;
    private static final int DOWNLOAD_COMPLETE = 0x06;

    //    private String mDownloadUrl = "http://static.gaoshouyou.com/d/22/94/822260b849944492caadd2983f9bb624.apk";
    private String mDownloadUrl = "https://upload-images.jianshu.io/upload_images/5601345-2c0ebf0bf44a31d5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";//图片
//    private String mDownloadUrl = "https://upload-images.jianshu.io/upload_images/5601345-2c0ebf0bf44a31d5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240";//apk
    private DownLoadUtil mUtil;

    private String filePath = Environment.getExternalStorageDirectory() + "/Download/http/picture.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_resume);
        ButterKnife.bind(this);
        mUtil = new DownLoadUtil();
    }

    @OnClick({R.id.start, R.id.stop, R.id.cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                start();
                break;
            case R.id.stop:
                stop();
                break;
            case R.id.cancel:
                cancel();
                break;
        }
    }

    private Handler mUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DOWNLOAD_PRE:
                    mSize.setText(Util.formatFileSize((Long) msg.obj));
                    mStart.setEnabled(false);
                    break;
                case DOWNLOAD_FAILE:
                    Toast.makeText(HttpResumeActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                case DOWNLOAD_STOP:
                    Toast.makeText(HttpResumeActivity.this, "暂停下载", Toast.LENGTH_SHORT).show();
                    mStart.setText("恢复");
                    mStart.setEnabled(true);
                    break;
                case DOWNLOAD_CANCEL:
                    mProgressBar.setProgress(0);
                    Toast.makeText(HttpResumeActivity.this, "取消下载", Toast.LENGTH_SHORT).show();
                    mStart.setEnabled(true);
                    mStart.setText("开始");
                    break;
                case DOWNLOAD_RESUME:
                    Toast.makeText(HttpResumeActivity.this, "恢复下载，恢复位置 ==> " + Util.formatFileSize((Long) msg.obj), Toast.LENGTH_SHORT).show();
                    mStart.setEnabled(false);
                    break;
                case DOWNLOAD_COMPLETE:
                    Toast.makeText(HttpResumeActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "方法：handleMessage: 存储地址：" + filePath);
                    mStart.setEnabled(true);
                    mCancel.setEnabled(false);
                    mStop.setEnabled(false);
                    break;
            }
        }
    };

    private void start() {
        mUtil.download(this, mDownloadUrl, filePath
                , new DownloadListener() {
                    long fileSize = 1;

                    @Override
                    public void onPreDownload(HttpURLConnection connection) {
                        super.onPreDownload(connection);
                        mProgressBar.setMax(100);
                        fileSize = connection.getContentLength();
                        mUpdateHandler.obtainMessage(DOWNLOAD_PRE, fileSize).sendToTarget();
                    }

                    @Override
                    public void onProgress(long currentLocation) {
                        super.onProgress(currentLocation);
                        mProgressBar.setProgress((int) (currentLocation * 100 / fileSize));
                    }

                    @Override
                    public void onStop(long stopLocation) {
                        super.onStop(stopLocation);
                        mUpdateHandler.obtainMessage(DOWNLOAD_STOP).sendToTarget();
                    }

                    @Override
                    public void onCancel() {
                        super.onCancel();
                        mUpdateHandler.obtainMessage(DOWNLOAD_CANCEL).sendToTarget();
                    }

                    @Override
                    public void onResume(long resumeLocation) {
                        super.onResume(resumeLocation);
                        mUpdateHandler.obtainMessage(DOWNLOAD_RESUME, resumeLocation).sendToTarget();
                    }

                    @Override
                    public void onFail() {
                        super.onFail();
                        mUpdateHandler.obtainMessage(DOWNLOAD_FAILE).sendToTarget();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        mUpdateHandler.obtainMessage(DOWNLOAD_COMPLETE).sendToTarget();
                    }
                });
    }

    private void stop() {
        mUtil.stopDownload();
    }

    private void cancel() {
        mUtil.cancelDownload();
    }
}
