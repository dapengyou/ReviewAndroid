package com.test.reviewandroid.recycleView.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.reviewandroid.R;
import com.test.reviewandroid.recycleView.bean.CategoryBean;
import com.test.reviewandroid.recycleView.bean.CommodityBean;
import com.test.reviewandroid.recycleView.bean.SubCatesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @createTime: 2019-07-10
 * @author: lady_zhou
 * @Description:
 */
public class CollapsibleAdapter extends BaseExpandableListAdapter {
    // 定义一个Context
    private Context context;
    // 定义一个LayoutInflater
    private LayoutInflater mInflater;
    // 定义一个List来保存列表数据
    private ArrayList<SubCatesBean> data_list = new ArrayList<>();

    // 定义一个构造方法
    public CollapsibleAdapter(Context context, ArrayList<SubCatesBean> datas) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data_list = datas;
    }

    // 刷新数据
    public void flashData(ArrayList<SubCatesBean> datas) {
        this.data_list = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 获取二级列表的内容
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data_list.get(groupPosition).getCommodity().get(childPosition);
    }

    // 获取二级列表的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 设置一级列表的view
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HodlerViewFather hodlerViewFather;
        if (convertView == null) {
            hodlerViewFather = new HodlerViewFather();

            convertView = mInflater.inflate(R.layout.item_collapsible_header, parent, false);
            hodlerViewFather.mTvName = convertView.findViewById(R.id.tv_name);
            // 新建一个TextView对象，用来显示一级标签上的大体描述的信息
            hodlerViewFather.mImageView = convertView.findViewById(R.id.image);

            convertView.setTag(hodlerViewFather);
        } else {
            hodlerViewFather = (HodlerViewFather) convertView.getTag();
        }

        // 一级列表右侧判断箭头显示方向
        if (isExpanded) {
            hodlerViewFather.mImageView.setImageResource(R.mipmap.icon_down);
        } else {
            hodlerViewFather.mImageView.setImageResource(R.mipmap.icon_up);
        }

        /**
         * 设置相应控件的内容
         */
        // 设置标题上的文本信息
        hodlerViewFather.mTvName.setText(data_list.get(groupPosition).getName());

        // 返回一个布局对象
        return convertView;
    }

    // 定义二级列表中的数据
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // 定义一个二级列表的视图类
        HolderView childrenView;
        ItemAdapter itemAdapter = new ItemAdapter(new ArrayList<CommodityBean>());
//        if (convertView == null) {
        childrenView = new HolderView();
        // 获取子视图的布局文件
//            convertView = mInflater.inflate(R.layout.item_collapsible, parent, false);
//            childrenView.mTbName = convertView.findViewById(R.id.tb_name);

        convertView = mInflater.inflate(R.layout.item_collapsible_body, parent, false);
        childrenView.mRecyclerView = convertView.findViewById(R.id.rv_list);

        childrenView.mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        itemAdapter.setNewData(data_list.get(groupPosition).getCommodity());
        childrenView.mRecyclerView.setAdapter(itemAdapter);

        // 这个函数是用来将holderview设置标签,相当于缓存在view当中
        convertView.setTag(childrenView);

//        } else {
//            childrenView = (HolderView) convertView.getTag();
//        }




        return convertView;
    }

    // 保存二级列表的视图类
    private class HolderView {
        //        ToggleButton mTbName;
        RecyclerView mRecyclerView;
    }

    // 获取二级列表的数量
    @Override
    public int getChildrenCount(int groupPosition) {
        return data_list.get(groupPosition).getCommodity().size();
    }

    // 获取一级列表的数据
    @Override
    public Object getGroup(int groupPosition) {
        return data_list.get(groupPosition);
    }

    // 获取一级列表的个数
    @Override
    public int getGroupCount() {
        return data_list.size();
    }

    /**
     * 指定位置相应的组视图
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 当选择子节点的时候，调用该方法(点击二级列表)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // 定义一个 一级列表的view类
    private class HodlerViewFather {
        TextView mTvName;
        ImageView mImageView;
    }

    //显示item的adapter
    private class ItemAdapter extends BaseQuickAdapter<CommodityBean, BaseViewHolder> {

        public ItemAdapter(List<CommodityBean> data) {
            super(R.layout.item_collapsible, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, CommodityBean bean) {
            baseViewHolder.setText(R.id.tb_name, bean.getName());

        }
    }
}
