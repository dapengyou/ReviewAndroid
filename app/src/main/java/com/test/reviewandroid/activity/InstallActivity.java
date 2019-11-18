package com.test.reviewandroid.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.test.reviewandroid.MainActivity;
import com.test.reviewandroid.R;
import com.test.reviewandroid.activity.activityToActivity.OneActivity;
import com.test.reviewandroid.activity.activityToFragment.ActivityToFragmentActivity;
import com.test.reviewandroid.activity.activityToService.ServiceDemoActivity;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InstallActivity extends AppCompatActivity {
    private static final String TAG = "InstallActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_install})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_install:
                Intent install = new Intent(Intent.ACTION_VIEW);
                File apkFile = new File(Environment.getExternalStorageDirectory() + "/upgrade/" + "newversion.zip");
                install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");

                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d(TAG, "方法：onViewClicked: " + apkFile.getAbsolutePath());
                startActivity(install);
                break;
        }
    }

}
