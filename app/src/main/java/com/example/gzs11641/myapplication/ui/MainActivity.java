package com.example.gzs11641.myapplication.ui;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.gzs11641.myapplication.R;
import com.example.gzs11641.myapplication.bean.TestInfo;
import com.example.gzs11641.myapplication.bean.TestInfo2;
import com.example.gzs11641.myapplication.databinding.ActivityMainBinding;
import com.example.gzs11641.myapplication.databinding.ItemTest2Binding;
import com.example.gzs11641.myapplication.databinding.ItemTestBinding;
import com.example.gzs11641.myapplication.listitem.adapter.MultiTypeRecyclerAdapter;
import com.example.gzs11641.myapplication.listitem.adapter.SimpleRecyclerAdpter;
import com.example.gzs11641.myapplication.viewmodel.ChronometerVM;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private ChronometerVM chronometerVM;
    private Chronometer chronometer;
    private Button btn_click;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        testMultiTypeAdapter();


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

    private void testSimpleAdapter() {
        final SimpleRecyclerAdpter<TestInfo> adapter = new SimpleRecyclerAdpter<TestInfo>(null, R.layout.item_test) {
            @Override
            protected void onCustomeView(ViewDataBinding binding, int position, TestInfo data) {
                System.out.println("=====getClass" + binding.getClass());
                ((ItemTestBinding) binding).tvTitle.setText(data.title);
            }
        };
        adapter.customRecyclerView(this, mBinding.recycleviewTest);

        mBinding.recycleviewTest.setAdapter(adapter);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<TestInfo> list = new ArrayList<>(40);
                        for (int i = 0; i < 40; i++) {
                            TestInfo testInfo = new TestInfo();
                            testInfo.title = "title" + Math.random();
                            list.add(testInfo);
                        }
                        adapter.setDataList(list);
                    }
                });
            }
        }, 3000, 3000);
    }

    private void testMultiTypeAdapter() {
        MultiTypeRecyclerAdapter.Param param = new MultiTypeRecyclerAdapter.Param(R.layout.item_test, TestInfo.class);
        MultiTypeRecyclerAdapter.Param param2 = new MultiTypeRecyclerAdapter.Param(R.layout.item_test2, TestInfo2.class);

        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new TestInfo("testInfo" + Math.random()));
            list.add(new TestInfo2("testInfo2---" + Math.random()));
            list.add("String=====");
        }

        MultiTypeRecyclerAdapter adapter = new MultiTypeRecyclerAdapter(list, param, param2) {
            @Override
            protected void onCustomeView(ViewDataBinding binding, int position, Object data) {
                if (binding instanceof ItemTestBinding) {
                    ItemTestBinding mBinding = (ItemTestBinding) binding;
                    TestInfo testInfo = (TestInfo) data;
                    mBinding.tvTitle.setText(testInfo.title);

                } else if (binding instanceof ItemTest2Binding) {
                    ItemTest2Binding mBinding = (ItemTest2Binding) binding;
                    TestInfo2 testInfo2 = (TestInfo2) data;
                    mBinding.tvTitle.setText("title2======"+testInfo2.title2);
                }

            }
        };
        adapter.customRecyclerView(this, mBinding.recycleviewTest);

        mBinding.recycleviewTest.setAdapter(adapter);


    }


}
