package com.example.gzs11641.myapplication.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.gzs11641.myapplication.R;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        RetrofitUtil.create();
    }


}
