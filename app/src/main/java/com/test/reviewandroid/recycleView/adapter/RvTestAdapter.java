package com.test.reviewandroid.recycleView.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.test.reviewandroid.R;

import java.util.List;

/**
 * @createTime: 2019-10-28
 * @author: lady_zhou
 * @Description:
 */
public class RvTestAdapter extends RecyclerView.Adapter<RvTestAdapter.RvTestViewHolder> {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private List<String> mList;
    private Context mContext;

    //headerView
    private View mHeaderView;

    public RvTestAdapter(Context context, List<String> list) {
        this.mList = list;
        this.mContext = context;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public RvTestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mHeaderView != null && i == TYPE_HEADER) {
            return new RvTestViewHolder(mHeaderView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv, viewGroup, false);
        return new RvTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvTestViewHolder rvTestViewHolder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) return;

        final int pos = getRealPosition(rvTestViewHolder);

        rvTestViewHolder.mTextView.setText(mList.get(pos));
        rvTestViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了" + pos, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @createTime: 2019-10-28
     * @author lady_zhou
     * @Description 得到除去header的位置
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mList.size() : mList.size() + 1;
    }

    public class RvTestViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public RvTestViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_text);
        }
    }

    /**
     * @createTime: 2019-10-28
     * @author lady_zhou
     * @Description 解决GridLayoutManager模式下header的展示问题
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            //转为GridLayoutManager
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {
                    return getItemViewType(i) == TYPE_HEADER ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }
}
