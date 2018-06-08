package com.example.gzs11641.myapplication.doctor.helper;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * @author Guan
 * @date Created on 2018/4/20
 */
public class CheckPermissionUtil {
    private static final String TAG = "CheckPermissionUtil";


    /**
     * 检查权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context context, String permission) {
        int permissionCheck = ContextCompat.checkSelfPermission(context,
                permission);
        boolean result = (permissionCheck == PackageManager.PERMISSION_GRANTED);
        printCheckPermissionResult(result, permission);
        return result;
    }


    /**
     * 检查 系统App 拥有的权限
     * 如：android:get_usage_stats
     *
     * @param context
     * @param op
     * @return
     */
    public static boolean checkSystemAppPermission(Context context, String op) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(op, android.os.Process.myUid(), context.getPackageName());
        boolean result = (mode == AppOpsManager.MODE_ALLOWED);
        printCheckPermissionResult(result, op);
        return result;
    }

    /**
     * 输出申请permission的结果
     *
     * @param result
     * @param permission
     */
    private static void printCheckPermissionResult(boolean result, String permission) {
        if (result) {
            Log.e(TAG, "已经授予权限permission:" + permission);
        } else {
            Log.e(TAG, "未授予权限permission:" + permission);
        }
    }

}
