package com.example.gzs11641.myapplication.doctor.viewmodel;

import android.app.AppOpsManager;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.gzs11641.myapplication.doctor.PowerConsumeFragment;
import com.example.gzs11641.myapplication.doctor.helper.UBCAppUsageStats;

import java.util.List;


public class PowerConsumeViewModel extends AndroidViewModel {

    private Application application;
    private UBCAppUsageStats appUsageStats;

    /**
     * 用于通知ListView更新UI
     */
    public final MediatorLiveData<List<String>> usageEvent = new MediatorLiveData<>();


    public PowerConsumeViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        appUsageStats = new UBCAppUsageStats(application);
    }

    /**
     * 开始统计
     */
    public void statsUsage() {
        List<String> usageList = appUsageStats.appStats();
        usageEvent.setValue(usageList);
    }


    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PowerConsumeFragment.REQUEST_CODE_APP_USAGE) {
            AppOpsManager appOps = (AppOpsManager) application.getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;

            mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), application.getPackageName());
            boolean granted = mode == AppOpsManager.MODE_ALLOWED;
            if (!granted) {
                Toast.makeText(application, "请开启该权限", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(application, "申请成功", Toast.LENGTH_SHORT).show();
                statsUsage();
            }
        }
    }
}
