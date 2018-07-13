package com.example.gzs11641.myapplication.ui;

import android.databinding.ViewDataBinding;

import com.example.gzs11641.myapplication.bean.TestInfo;
import com.example.gzs11641.myapplication.databinding.ItemTestBinding;
import com.example.gzs11641.myapplication.listitem.adapter.RecyclerAdpter;

import java.util.List;

public class TestAdapter extends RecyclerAdpter<TestInfo> {


    public TestAdapter(List mDatas, int layoutId) {
        super(mDatas, layoutId);
    }


    @Override
    protected void onCustomeView(ViewDataBinding binding, int position, TestInfo data) {
        ((ItemTestBinding) binding).tvTitle.setText(data.title);

    }
}
