package com.example.gzs11641.myapplication.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.gzs11641.myapplication.R;
import com.example.gzs11641.myapplication.viewmodel.ChronometerVM;

public class MainActivity extends AppCompatActivity {

    private TextView tv_hello;
    private ChronometerVM chronometerVM;
    private Chronometer chronometer;
    private Button btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_hello = findViewById(R.id.hello_textview);
        chronometer = findViewById(R.id.chronometer);
        btn_click = findViewById(R.id.btn_click);

        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
        chronometerVM = ViewModelProviders.of(this).get(ChronometerVM.class);
        subscribeUi(chronometerVM);
    }

    private void subscribeUi(ChronometerVM viewModel) {

        // Update the list when the data changes
        viewModel.getTime().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {
                tv_hello.setText(aLong + "");
            }
        });
    }


}
