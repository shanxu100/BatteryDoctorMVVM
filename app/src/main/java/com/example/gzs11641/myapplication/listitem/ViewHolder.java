package com.example.gzs11641.myapplication.listitem;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewHolder extends RecyclerView.ViewHolder {


    private ViewDataBinding binding;

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }

    public ViewHolder(View itemView,ViewDataBinding binding) {
        super(itemView);
        this.binding = binding;
    }

    public static class EmptyLayoutViewHolder extends ViewHolder{

        public EmptyLayoutViewHolder(View itemView, ViewDataBinding binding) {
            super(itemView, binding);
        }
    }


}
