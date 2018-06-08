package com.example.gzs11641.myapplication.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class ChronometerVM extends AndroidViewModel {

    private MediatorLiveData<Long> time;
    private static final String TAG = "TAG";
    private Handler handler = new Handler(Looper.getMainLooper());
    private Timer timer;

    public ChronometerVM(@NonNull Application application) {
        super(application);
        time = new MediatorLiveData<>();

        ValuePP();
        Log.e(TAG, "初始化ViewModel:ChronometerVM");
    }

    public LiveData<Long> getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time.setValue(time);

    }

    /**
     * 模拟值的改变
     */
    private void ValuePP() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setTime(System.currentTimeMillis() / 1000);
                        System.out.println("timer task=====");
                    }
                });
            }
        }, 0, 2000);

    }

    /**
     * activity finish的时候会调用；但是旋转屏幕的时候不会调用
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.e(TAG, "viewmodel====onCleared");
        timer.cancel();
    }
}
