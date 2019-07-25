package com.test.reviewandroid.recycleView.userComplexLayout;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.reviewandroid.R;
import com.test.reviewandroid.recycleView.adapter.CollapsibleAdapter;
import com.test.reviewandroid.recycleView.bean.CategoryBean;
import com.test.reviewandroid.recycleView.bean.SubCatesBean;
import com.test.reviewandroid.util.FileUtil;
import com.test.reviewandroid.util.ScreenUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollapsibleActivity extends AppCompatActivity {
    private PopupWindow mMenuPop;
    private CollapsibleAdapter adapter;
    private RecyclerView mRvList;
    private ExpandableListView mExpandableListView;
    private ArrayList<SubCatesBean> mDatas = new ArrayList<>();
    private ArrayList<CategoryBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);
        ButterKnife.bind(this);

    }

    private void initListener() {
        // 设置ExpandableListView的监听事件
        // 设置一级item点击的监听器
        mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(CollapsibleActivity.this, mDatas.get(arg2).getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });

        // 设置二级item点击的监听器，同时在Adapter中设置isChildSelectable返回值true，同时二级列表布局中控件不能设置点击效果
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2, int arg3, long arg4) {
                // TODO Auto-generated method stub
                Toast.makeText(CollapsibleActivity.this, mDatas.get(arg2).getCommodity().get(arg3).getName(), Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    @OnClick(R.id.bt_scree)
    public void onViewClicked() {
        initPopuptWindow();
    }

    protected void initPopuptWindow() {
        View view = getLayoutInflater().inflate(R.layout.pop_list, null);

//        mRvList = view.findViewById(R.id.rv_list);
        mExpandableListView = view.findViewById(R.id.expandablelist);

        if (mMenuPop == null) {
            //宽度用屏幕的四分之三
            mMenuPop = new PopupWindow(view, ScreenUtils.getScreenWidth(this) / 4 * 3, LinearLayout.LayoutParams.MATCH_PARENT, true);
        }

        initJsonData();
        setAdapter();
        initListener();

        mMenuPop.setBackgroundDrawable(new ColorDrawable(0x50000000));
        mMenuPop.setAnimationStyle(R.style.popupWindowAnimRight);
        mMenuPop.setFocusable(true);
        mMenuPop.showAtLocation(view, Gravity.RIGHT, 0, ScreenUtils.getStatusBarHeight(this));
        mMenuPop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                if (mMenuPop != null) {
                    mMenuPop.dismiss();
                    mMenuPop = null;
                }

            }
        });
    }

    /**
     * 自定义setAdapter
     */
    private void setAdapter() {
            adapter = new CollapsibleAdapter(this, mDatas);
            mExpandableListView.setAdapter(adapter);
//        if (adapter == null) {
//        } else {
//            adapter.flashData(mDatas);
//            adapter.notifyDataSetChanged();
//
//        }
    }

    /**
     * 初始化解析后的数据，进行显示
     */
    private void initJsonData() {
        mDatas.clear();
        //获得json
        String json = FileUtil.readStringFromAsset(this, "categoryOfOne.json");
        Gson gson = new Gson();
        mList = new ArrayList<CategoryBean>();
        Type type = new TypeToken<ArrayList<CategoryBean>>() {
        }.getType();
        mList = gson.fromJson(json, type);

        for (CategoryBean categoryBean : mList) {
            mDatas.addAll(categoryBean.getSubCates());
        }

    }
}
