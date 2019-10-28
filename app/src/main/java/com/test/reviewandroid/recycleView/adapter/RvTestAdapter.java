package com.test.reviewandroid.recycleView.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.reviewandroid.R;

import java.util.List;

/**
 * @createTime: 2019-10-28
 * @author: lady_zhou
 * @Description:
 */
public class RvTestAdapter extends RecyclerView.Adapter<RvTestAdapter.RvTestViewHolder> {
    private List<String> list;
    private Context context;

    public RvTestAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RvTestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, viewGroup, false);
        return new RvTestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RvTestViewHolder rvTestViewHolder, int position) {
        rvTestViewHolder.mTextView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RvTestViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public RvTestViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_text);
        }
    }
}
