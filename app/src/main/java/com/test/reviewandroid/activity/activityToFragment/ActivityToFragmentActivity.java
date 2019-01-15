package com.test.reviewandroid.activity.activityToFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityToFragmentActivity extends AppCompatActivity implements MyFragment.FragmentCallBack {

    @BindView(R.id.tv_back)
    TextView mTvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    //实现回调接口
    @Override
    public void sendContent(String info) {
        if (info != null) {
            mTvBack.setText("这是Activity\n返回的值是：" + info);
        }
    }
}
