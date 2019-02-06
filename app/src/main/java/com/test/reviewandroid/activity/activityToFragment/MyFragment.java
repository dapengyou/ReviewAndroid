package com.test.reviewandroid.activity.activityToFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.reviewandroid.MainActivity;
import com.test.reviewandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @createTime: 2019/1/15
 * @author: lady_zhou
 * @Description:
 */
public class MyFragment extends Fragment {
    private static final String TAG = "Fragment";
    @BindView(R.id.tv_text)
    TextView mTvText;
    Unbinder unbinder;

    //回传Activity第二步  定义属性
    private FragmentCallBack mFragmentCallBack;

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "Fragment: 调用onAttach");
        super.onAttach(context);
        mFragmentCallBack = (FragmentCallBack) getActivity();//回传Activity第三步  获取相应的Activity
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Fragment: 调用onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Fragment: 调用onCreateView");
        View view = inflater.inflate(R.layout.activity_fragment, container, false);

        unbinder = ButterKnife.bind(this, view);

        //收到Activity传来的值第一步 获取从Activity中传过来的全部值
        savedInstanceState = this.getArguments();
        //收到Activity传来的值第二步  获取某一个值
        String message = savedInstanceState.getString("message");
        mTvText.setText("我是fragment\n 传过来的值：" + message);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "Fragment: 调用 onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "Fragment: 调用 onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "Fragment: 调用 onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "Fragment: 调用 onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "Fragment: 调用 onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "Fragment: 调用 onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Fragment: 调用 onDestroy");
        super.onDestroy();
    }

    /**
     * @return : void
     * @date 创建时间: 2019/1/16
     * @author lady_zhou
     * @Description 将传递进来的Activity对象释放掉
     */
    @Override
    public void onDetach() {
        Log.d(TAG, "Fragment: 调用 onDetach");
        super.onDetach();
        mFragmentCallBack = null;
    }

    @OnClick({R.id.bt_button, R.id.bt_show_dialog})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_button:
                mFragmentCallBack.sendContent("从Fragment中回传回来的值");//回传Activity第四步  调用接口中的方法
                break;
            case R.id.bt_show_dialog:
                showDialog();
                break;
        }
    }

    /**
     * @author lady_zhou
     * @createTime: 2019/2/6
     * @Description 回传Activity第一步  定义回调接口
     */
    public interface FragmentCallBack {
        void sendContent(String info);
    }

    /**
     * @createTime: 2019/2/6
     * @author  lady_zhou
     * @Description 显示dialog
     */
    private void showDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder dialog =
                new AlertDialog.Builder(getActivity());
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
}
