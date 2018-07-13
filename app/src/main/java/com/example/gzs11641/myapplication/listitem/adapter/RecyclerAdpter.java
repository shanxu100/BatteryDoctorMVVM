package com.example.gzs11641.myapplication.listitem.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.gzs11641.myapplication.R;
import com.example.gzs11641.myapplication.listitem.ViewHolder;

import java.util.List;

public abstract class RecyclerAdpter<T> extends RecyclerView.Adapter<ViewHolder> {

    private List<T> mDatas;

    private int layoutId;



    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_NORMAL = 1;

    private static final String TAG = "RecyclerAdpter";


    public RecyclerAdpter(List<T> mDatas, int layoutId) {
        this.mDatas = mDatas;
        this.layoutId = layoutId;
    }

    public void setDataList(List<T> mDatas) {
        this.mDatas.clear();
        this.mDatas.addAll(mDatas);
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas == null || mDatas.size() == 0) {
            return TYPE_EMPTY;
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_NORMAL) {
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, layoutId, parent, false);
            ViewHolder viewHolder = new ViewHolder(binding.getRoot(), binding);
            return viewHolder;
        } else {
            //viewType==TYPE_EMPTY
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.layout_empty, parent, false);
            ViewHolder.EmptyLayoutViewHolder viewHolder = new ViewHolder.EmptyLayoutViewHolder(binding.getRoot(), binding);
            return viewHolder;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder instanceof ViewHolder.EmptyLayoutViewHolder) {
            Log.e(TAG, "数据为空，显示默认layout");
        } else {
            if (position < mDatas.size()) {
                onCustomeView(holder.getBinding(), position, mDatas.get(position));
//                holder.getBinding().executePendingBindings();
            } else {
                Log.e(TAG, "数组越界------position=" + position + " l ist.size=" + mDatas.size());
            }
        }

    }

    ;

    @Override
    public int getItemCount() {
        if (mDatas == null || mDatas.size() == 0) {
            return 1;
        }
        return mDatas.size();
    }


    protected abstract void onCustomeView(ViewDataBinding binding, int position, T data);
}
