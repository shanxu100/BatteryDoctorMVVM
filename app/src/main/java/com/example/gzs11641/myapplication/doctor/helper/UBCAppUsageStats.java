package com.example.gzs11641.myapplication.doctor.helper;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 统计App使用信息
 *
 * @author Guan
 * @date Created on 2018/4/20
 */
public class UBCAppUsageStats {

    private static final String TAG = "UBCAppUsageStats";
    private UsageStatsManager usm;
    private Context context;

    private UsageStatsManager getUsageStatsManager(Context context) {
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        return usm;
    }

    public UBCAppUsageStats(Context context) {
        this.context = context;
        usm = getUsageStatsManager(context);
    }

    public List<String> appStats() {
        //检查权限
        if (!CheckPermissionUtil.checkSystemAppPermission(context, AppOpsManager.OPSTR_GET_USAGE_STATS)) {
            return new ArrayList<>(0);
        }
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, -1);
        long startTime = calendar.getTimeInMillis();

        Log.i(TAG, "Range start:" + startTime);
        Log.i(TAG, "Range end:" + endTime);

        List<UsageStats> usageStatsList = usm.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                startTime,
                endTime);
//        printUsageStats(usageStatsList);
        return toStrList(usageStatsList);

// 或者
//        UsageEvents uEvents = usm.queryEvents(startTime, endTime);
//        Log.i(TAG, "print UsageEvent: ");
//
//        while (uEvents.hasNextEvent()) {
//            UsageEvents.Event e = new UsageEvents.Event();
//            uEvents.getNextEvent(e);
//            if (e != null) {
//                Log.i(TAG, "Event: " + e.getPackageName() + "\t" + e.getTimeStamp());
//            }
//        }

    }

    private List<String> toStrList(List<UsageStats> usageStatsList) {
        List<String> list = new ArrayList<>(usageStatsList.size());
        for (UsageStats u : usageStatsList) {
            long totalTime = u.getTotalTimeInForeground() / 1000 / 60;
            if (totalTime == 0) {
                continue;
            }
            list.add(u.getPackageName() + "\n\n前台运行总时间" + totalTime);
        }
        return list;
    }

    private void printUsageStats(List<UsageStats> usageStatsList) {
        StringBuilder sb = new StringBuilder();
        sb.append("printUsageStats:\n");
        for (UsageStats u : usageStatsList) {

            sb.append("Pkg: " + u.getPackageName() + "\t" + "ForegroundTime: "
                    + u.getTotalTimeInForeground() + "\n");
        }
        Log.i(TAG, sb.toString());
    }

}
