package com.test.reviewandroid.recycleView.userComplexLayout;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.test.reviewandroid.R;
import com.test.reviewandroid.recycleView.adapter.CategoryAdapter;
import com.test.reviewandroid.recycleView.adapter.SubCatesAdapter;
import com.test.reviewandroid.recycleView.bean.CategoryBean;
import com.test.reviewandroid.recycleView.bean.CommodityBean;
import com.test.reviewandroid.recycleView.bean.SubCatesBean;
import com.test.reviewandroid.util.FileUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryActivity extends AppCompatActivity {

    @BindView(R.id.rv_category)
    RecyclerView mRvCategory;
    @BindView(R.id.rv_category_info)
    RecyclerView mRvCategoryInfo;
    @BindView(R.id.srl_list)
    SmartRefreshLayout mSrlList;

    private List<SubCatesBean> mSubCatesBeans = new ArrayList<>();
    private List<CategoryBean> mCategoryBeans = new ArrayList<>();
    private List<MultiItemEntity> mDatas = new ArrayList<>();


    private CategoryAdapter mCategoryAdapter;
    private SubCatesAdapter mSubCatesAdapter;

    private int mIndex = 0;//选中了哪个item
    private boolean isRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        initView();
        initData();
        initLinstener();
    }

    /**
     * @createTime: 2019-07-05
     * @author lady_zhou
     * @Description 初始化点击监听
     */
    private void initLinstener() {
        //点击右侧的item
        mSubCatesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MultiItemEntity multiItemEntity = mSubCatesAdapter.getData().get(i);
                if (multiItemEntity.getItemType() == SubCatesAdapter.TYPE_SUBCATES) {
                    //转化成对应的bean
                    CommodityBean commodityBean = (CommodityBean) multiItemEntity;
                    Toast.makeText(CategoryActivity.this, commodityBean.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //点击第一列内容变换第二列内容
        mCategoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                changeData(i);
                mIndex = i;
            }
        });

        //两列联动滚动换数据
        mRvCategoryInfo.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Toast.makeText(CategoryActivity.this, "滑动", Toast.LENGTH_SHORT).show();

            }
        });
        //SwipeRefreshLayout滑动监听
        mSrlList.setEnableLoadmoreWhenContentNotFull(true);//不满一页开启上拉加载
        mSrlList.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (mIndex > 0) {
                    mIndex--;
                }
                changeData(mIndex);
                mSrlList.finishRefresh();
            }

            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mIndex < mCategoryBeans.size() - 1) {
                    mIndex++;
                }
                changeData(mIndex);
                mSrlList.finishLoadmore();
            }
        });

    }

    private void changeData(int position) {
        mDatas.clear();//清理数据，避免在原有数据后面加值
        CategoryBean categoryBean = mCategoryAdapter.getData().get(position);
        mCategoryAdapter.setChecked(position);

        mSubCatesBeans = categoryBean.getSubCates();
        for (SubCatesBean bean : mSubCatesBeans) {
            mDatas.add(bean);
            mDatas.addAll(bean.getCommodity());
        }
        mSubCatesAdapter.setNewData(mDatas);
    }

    /**
     * @createTime: 2019-07-04
     * @author lady_zhou
     * @Description 初始化View
     */
    private void initView() {
        mRvCategory.setLayoutManager(new LinearLayoutManager(this));
        mCategoryAdapter = new CategoryAdapter(new ArrayList<CategoryBean>());
        mRvCategory.setAdapter(mCategoryAdapter);
//        mCategoryAdapter.bindToRecyclerView(mRvCategory);


        mRvCategoryInfo.setLayoutManager(new GridLayoutManager(this, 3));
        mSubCatesAdapter = new SubCatesAdapter(new ArrayList<MultiItemEntity>());
        mRvCategoryInfo.setAdapter(mSubCatesAdapter);
//        mSubCatesAdapter.bindToRecyclerView(mRvCategoryInfo);

    }

    /**
     * @createTime: 2019-07-04
     * @author lady_zhou
     * @Description 初始化data
     */
    private void initData() {

        //获得json
        String json = FileUtil.readStringFromAsset(this, "category.json");
//        String json = FileUtil.readStringFromAsset(this, "categoryOfOne.json");
        Gson gson = new Gson();
        mCategoryBeans = new ArrayList<CategoryBean>();
        Type type = new TypeToken<ArrayList<CategoryBean>>() {
        }.getType();
        mCategoryBeans = gson.fromJson(json, type);

        //一股脑全部显示
//        for (CategoryBean categoryBean : mCategoryBeans) {
//            mSubCatesBeans.addAll(categoryBean.getSubCates());
//        }
        //显示第一条内的所有数据
        CategoryBean categoryBean = mCategoryBeans.get(0);
        mSubCatesBeans = categoryBean.getSubCates();


        //装载数据
        if (mSubCatesBeans != null && mSubCatesBeans.size() > 0) {
            for (SubCatesBean bean : mSubCatesBeans) {
                mDatas.add(bean);
                mDatas.addAll(bean.getCommodity());
            }
            mCategoryAdapter.setNewData(mCategoryBeans);
            mSubCatesAdapter.setNewData(mDatas);
        }


        //设置布局所占长度
        mSubCatesAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                if (mSubCatesAdapter.getData().get(i).getItemType() == SubCatesAdapter.TYPE_CATEGORY) {
                    return 3;//总共有三份   全占
                } else {
                    return 1;
                }
            }
        });
    }

}
