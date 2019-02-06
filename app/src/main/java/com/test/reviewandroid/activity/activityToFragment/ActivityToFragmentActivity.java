package com.test.reviewandroid.activity.activityToFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author lady_zhou
 * @createTime: 2019/2/6
 * @Description Activity与Fragment通信
 */
public class ActivityToFragmentActivity extends AppCompatActivity implements MyFragment.FragmentCallBack {
    private static final String TAG = "Fragment";
    @BindView(R.id.tv_back)
    TextView mTvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Activity: 调用onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_fragment);
        ButterKnife.bind(this);

        //第一步获取FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        //第二步获取FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //第三步创建需要添加的Fragment
        MyFragment mFragment = new MyFragment();

        //第四步创建Bundle对象
        Bundle bundle = new Bundle();

        //第五步往bundle中添加数据
        bundle.putString("message", "Activity往Fragment中传值");

        //第六步  将数据添加在fragment中
        mFragment.setArguments(bundle);

        //第七步  动态添加fragment
        fragmentTransaction.replace(R.id.fragment, mFragment);
        fragmentTransaction.commit();

    }

    /**
     * @param info :  Activity返回的值
     * @return : void
     * @date 创建时间: 2019/2/6
     * @author lady_zhou
     * @Description 实现回调接口
     */
    @Override
    public void sendContent(String info) {
        if (info != null) {
            mTvBack.setText("这是Activity\n返回的值是：" + info);
        }
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "Activity: 调用onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Activity: 调用onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "Activity: 调用onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "Activity: 调用onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Activity: 调用onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "Activity: 调用onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "Activity: 调用onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "Activity: " + "调用onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    /**
     * @createTime: 2019/2/6
     * @author lady_zhou
     * @Description 显示dialog
     */
    private void showDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder dialog =
                new AlertDialog.Builder(this);
        dialog.setTitle("我是Dialog");
        dialog.setMessage("你要点击哪一个按钮呢?");
        dialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        dialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        // 显示
        dialog.show();
    }

    @OnClick(R.id.bt_button)
    public void onViewClicked() {
        showDialog();
    }
}
