package com.example.gzs11641.myapplication.rxJava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.gzs11641.myapplication.R;

import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

public class RxActivity extends AppCompatActivity {


    Action1<RxUtil.BaseResult> onNextAction1 = new Action1<RxUtil.BaseResult>() {
        @Override
        public void call(RxUtil.BaseResult baseResult) {
            Log.e(TAG, "onNext----" + baseResult.str + " currentThread:" + Thread.currentThread().getName());

        }
    };
    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            // Error handling
        }
    };
    Action0 onCompletedAction = new Action0() {
        @Override
        public void call() {
            Log.e(TAG, "completed");
        }
    };

    private static final String TAG = "RxActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        initData();
    }

    private void initData() {
        String[] ss = new String[]{"1", "2"};
        RxUtil.getObservable(new RxUtil.BaseSource() {
            @Override
            public RxUtil.BaseResult call() throws Exception {
                Log.e(TAG, "模拟耗时操作......Thread: " + Thread.currentThread().getName());
                Thread.sleep(5000);
                Log.e(TAG, "耗时操作结束");
                RxUtil.BaseResult baseResult = new RxUtil.BaseResult();
                baseResult.str = "balabala";
                return baseResult;
            }
        })


//                .subscribe(onNextAction1, onErrorAction, onCompletedAction);
                .subscribe(new Subscriber<RxUtil.BaseResult>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onNext(RxUtil.BaseResult baseResult) {
                        Log.e(TAG, "onNext----" + baseResult.str + " currentThread:" + Thread.currentThread().getName());
                    }

                });


    }

}
