package com.test.reviewandroid.activity.activityToFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    @BindView(R.id.tv_text)
    TextView mTvText;
    Unbinder unbinder;

    //回传Activity第二步  定义属性
    private FragmentCallBack mFragmentCallBack;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mFragmentCallBack = (FragmentCallBack) getActivity();//回传Activity第三步  获取相应的Activity
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_button)
    public void onViewClicked() {
        mFragmentCallBack.sendContent("从Fragment中回传回来的值");//回传Activity第四步  调用接口中的方法
    }

    //回传Activity第一步  定义回调接口
    public  interface FragmentCallBack{
         void sendContent(String info);
    }
}
