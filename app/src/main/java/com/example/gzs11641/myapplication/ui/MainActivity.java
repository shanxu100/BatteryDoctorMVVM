package com.example.gzs11641.myapplication.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.gzs11641.myapplication.R;
import com.example.gzs11641.myapplication.bean.TestInfo;
import com.example.gzs11641.myapplication.databinding.ActivityMainBinding;
import com.example.gzs11641.myapplication.databinding.ItemTestBinding;
import com.example.gzs11641.myapplication.listitem.adapter.RecyclerAdpter;
import com.example.gzs11641.myapplication.viewmodel.ChronometerVM;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ChronometerVM chronometerVM;
    private Chronometer chronometer;
    private Button btn_click;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        List<TestInfo> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            TestInfo testInfo = new TestInfo();
            testInfo.title = "title" + i;
            list.add(testInfo);
        }

        mBinding.recycleviewTest.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mBinding.recycleviewTest.setAdapter(new RecyclerAdpter<TestInfo>(null,R.layout.item_test) {

            @Override
            protected void onCustomeView(ViewDataBinding binding, int position, TestInfo data) {

                ((ItemTestBinding)binding).tvTitle.setText(data.title);
            }
        });


//        tv_hello = findViewById(R.id.hello_textview);
//        chronometer = findViewById(R.id.chronometer);
//        btn_click = findViewById(R.id.btn_click);

//        btn_click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//        chronometerVM = ViewModelProviders.of(this).get(ChronometerVM.class);
//        subscribeUi(chronometerVM);
    }

    private void subscribeUi(ChronometerVM viewModel) {

        // Update the list when the data changes
        viewModel.getTime().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {
//                mBinding.helloTextview.setText(aLong + "");
            }
        });
    }


}
