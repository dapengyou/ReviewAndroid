package com.test.reviewandroid.activity.activityToFragment;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment, container,false);

        unbinder = ButterKnife.bind(this, view);

        //第一步 获取从Activity中传过来的全部值
        savedInstanceState = this.getArguments();
        //第二步  获取某一个值
        String message = savedInstanceState.getString("message");
        mTvText.setText("我是fragment\n 传过来的值：" + message);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
