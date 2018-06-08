package com.example.gzs11641.myapplication.doctor;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.gzs11641.myapplication.doctor.helper.UBCAppUsageStats;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class AppStatusIntentService extends IntentService {


    public AppStatusIntentService() {
        super("AppStatusIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        UBCAppUsageStats stats=new UBCAppUsageStats(this);
        stats.appStats();
    }


}
