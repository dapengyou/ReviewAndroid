package com.test.reviewandroid.recycleView.BySelfRecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.test.reviewandroid.R;
import com.test.reviewandroid.recycleView.adapter.RvTestAdapter;

import java.util.ArrayList;
import java.util.List;

public class RvTestActivity extends AppCompatActivity {
    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_test);

        initView();
    }

    private void initView() {
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new GridLayoutManager(this, 3));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("这是第" + i + "条数据");
        }

        RvTestAdapter adapter = new RvTestAdapter(this, list);//传入数据源
        mRv.setAdapter(adapter);
    }
}
