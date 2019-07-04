package com.test.reviewandroid.recycleView.userComplexLayout;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stx.xhb.pagemenulibrary.PageMenuLayout;
import com.stx.xhb.pagemenulibrary.holder.AbstractHolder;
import com.stx.xhb.pagemenulibrary.holder.PageMenuViewHolderCreator;
import com.test.reviewandroid.R;
import com.test.reviewandroid.recycleView.MockData.MockData;
import com.test.reviewandroid.recycleView.adapter.ComplexlayoutAdapter;
import com.test.reviewandroid.recycleView.bean.CommodityBean;
import com.test.reviewandroid.recycleView.bean.HeaderBean;
import com.test.reviewandroid.recycleView.bean.LinkBannerBean;
import com.test.reviewandroid.util.FileUtil;
import com.test.reviewandroid.util.GlideUtils;
import com.test.reviewandroid.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;

public class ComplexLayoutActivity extends AppCompatActivity {
    private static final String TAG = "ComplexLayoutActivity";
    @BindView(R.id.rl_list)
    RecyclerView mRlList;
    @BindView(R.id.srl_list)
    SwipeRefreshLayout mSrlList;

    private View mHeaderView;
    private HeaderViewHolder mHeaderViewHolder;

    private List<LinkBannerBean> bannerBeans = new ArrayList<>();
    private List<CommodityBean> commodityBeans = new ArrayList<>();

    private ComplexlayoutAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_layout);
        ButterKnife.bind(this);

        initRecycleView();
        initData();
    }

    /**
     * @createTime: 2019-07-03
     * @author lady_zhou
     * @Description 初始化数据
     */
    private void initData() {
        //userbean练习
//        String json = FileUtil.readStringFromAsset(this, "user.json");
//        Gson gson = new Gson();
//        User user = gson.fromJson(json, User.class);//将json数据转换成user类实体
//        Log.d(TAG, "方法：initData: " + user.getName() + user.getAge());

        //listbean  练习
//        String json = FileUtil.readStringFromAsset(this, "listBean.json");
//        Gson gson = new Gson();
//        List<LinkBannerBean> linkBannerBeans = new ArrayList<LinkBannerBean>();
//
//        Type type = new TypeToken<ArrayList<LinkBannerBean>>() {
//        }.getType();
//
//        linkBannerBeans = gson.fromJson(json, type);
//
//        for (LinkBannerBean linkBannerBean : linkBannerBeans) {
//            Log.d(TAG, "方法：initData: " + linkBannerBean.getImage());
//        }

        //获得json
        String json = FileUtil.readStringFromAsset(this, "headerJson.json");

        Gson gson = new Gson();
        HeaderBean headerBean = gson.fromJson(json, HeaderBean.class);//将json数据转换成HeaderBean类实体

        bannerBeans = headerBean.getLinkBanner();
        commodityBeans = headerBean.getCommodity();

        if (bannerBeans != null && bannerBeans.size() > 0) {
            mHeaderViewHolder.mBgaBanner.setData(bannerBeans, null);
        }

        if (commodityBeans != null && commodityBeans.size() > 0) {
            initPageMenu(commodityBeans);
        }
    }

    /**
     * @createTime: 2019-07-04
     * @author lady_zhou
     * @Description 分页菜单
     */
    private void initPageMenu(final List<CommodityBean> commodityBeans) {
        mHeaderViewHolder.mPageMenuLayout.setPageDatas(commodityBeans, new PageMenuViewHolderCreator() {
            @Override
            public AbstractHolder createHolder(View itemView) {
                return new AbstractHolder<CommodityBean>(itemView) {
                    private TextView menuName;
                    private ImageView menuImage;

                    @Override
                    protected void initView(View itemView) {
                        menuName = itemView.findViewById(R.id.tv_name);
                        menuImage = itemView.findViewById(R.id.iv_image);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) ((float) ScreenUtils.getScreenWidth(ComplexLayoutActivity.this) / 4.0f));
                        itemView.setLayoutParams(layoutParams);
                    }

                    @Override
                    public void bindView(RecyclerView.ViewHolder holder, CommodityBean data, int pos) {
                        //设置内容
                        menuName.setText(data.getName());
                        GlideUtils.loadImage(ComplexLayoutActivity.this, menuImage, data.getLogopath());
                    }
                };
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_home_menu;
            }
        });
    }

    /**
     * @createTime: 2019-07-02
     * @author lady_zhou
     * @Description 初始化recycleView
     */
    private void initRecycleView() {
        initHeaderView();
        //纵向RecycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //流式布局
//        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRlList.setLayoutManager(linearLayoutManager);
        mAdapter = new ComplexlayoutAdapter(MockData.getCommodityDatas(10, true));

        mAdapter.addHeaderView(mHeaderView);
        mRlList.setAdapter(mAdapter);

    }

    /**
     * @createTime: 2019-07-02
     * @author lady_zhou
     * @Description 初始化头view
     */
    private void initHeaderView() {
        //1.找到headerview
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_complex_header, null);
        //2.建立Holder
        mHeaderViewHolder = new HeaderViewHolder(mHeaderView);

        mHeaderViewHolder.mBgaBanner.setAdapter(new BGABanner.Adapter<ImageView, LinkBannerBean>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, LinkBannerBean model, int position) {
                GlideUtils.loadImage(ComplexLayoutActivity.this, itemView, model.getImage());
            }

        });
        mHeaderViewHolder.mBgaBanner.setAutoPlayAble(true);

    }

    /**
     * @author lady_zhou
     * @createTime: 2019-07-02
     * @Description 初始化header里的控件, 保存View引用的容器类
     */
    class HeaderViewHolder {
        @BindView(R.id.bga_banner)
        BGABanner mBgaBanner;

        @BindView(R.id.pageMenuLayout)
        PageMenuLayout mPageMenuLayout;

        public HeaderViewHolder(View headerview) {
            ButterKnife.bind(this, headerview);
        }

    }
}
