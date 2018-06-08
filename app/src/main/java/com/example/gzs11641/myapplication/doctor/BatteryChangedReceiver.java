package com.example.gzs11641.myapplication.doctor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableLong;
import android.os.BatteryManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.gzs11641.myapplication.doctor.viewmodel.PowerMonitorViewModel;

public class BatteryChangedReceiver extends BroadcastReceiver {


    private PowerMonitorViewModel monitorViewModel;

    private static final String TAG = "BatteryChangedReceiver";


    public BatteryChangedReceiver(){

    }

    public BatteryChangedReceiver(PowerMonitorViewModel monitorViewModel) {
        this.monitorViewModel = monitorViewModel;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_CHANGED)) {
            // 当前电池的电压
            int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            // 电池的健康状态
            String health = getBatteryHealth(intent);
            // 电池当前的电量, 它介于0和 EXTRA_SCALE之间
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            // 电池电量的最大值
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            // 当前手机使用的是哪里的电源
            String plugged = getPlugged(intent);
            //电池状态
            String status = getStatus(intent);
            // 电池使用的技术。比如，对于锂电池是Li-ion
            String technology = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
            // 当前电池的温度
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);


            if (monitorViewModel!=null){
                monitorViewModel.updateEvent.setValue(null);
                monitorViewModel.now.set(System.currentTimeMillis());
                monitorViewModel.level.set(level);
                monitorViewModel.health.set(health);
                monitorViewModel.voltage.set(voltage/1000.0f);
                monitorViewModel.status.set(status);
                monitorViewModel.temp.set(temperature/10f);
            }

        } else if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_LOW)) {
            // 表示当前电池电量低
        } else if (action.equalsIgnoreCase(Intent.ACTION_BATTERY_OKAY)) {
            // 表示当前电池已经从电量低恢复为正常
            System.out.println(
                    "BatteryChangedReceiver ACTION_BATTERY_OKAY---");
        }
    }

    /**
     * 获取电池的健康状态
     *
     * @param intent
     * @return
     */
    private String getBatteryHealth(Intent intent) {
        int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
        String healthStr = "";
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthStr = "BATTERY_HEALTH_COLD";
                break;
            case BatteryManager.BATTERY_HEALTH_COLD:
                healthStr = "BATTERY_HEALTH_COLD";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthStr = "BATTERY_HEALTH_DEAD";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthStr = "BATTERY_HEALTH_OVERHEAT";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthStr = "BATTERY_HEALTH_OVER_VOLTAGE";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                healthStr = "BATTERY_HEALTH_UNKNOWN";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthStr = "BATTERY_HEALTH_UNSPECIFIED_FAILURE";
                break;
            default:
                healthStr = "未知";
                break;
        }
        return healthStr;
    }


    /**
     * 充电方式
     *
     * @param intent
     * @return
     */
    private String getPlugged(Intent intent) {
        int pluged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        String pluggedStr = "";
        switch (pluged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                // 电源是AC charger.[应该是指充电器]
                pluggedStr = "电源是AC charger";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                // 电源是USB port
                pluggedStr = "电源是USB port";
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                pluggedStr = "无线充电";
                break;
            default:
                break;
        }
        return pluggedStr;
    }


    /**
     * 电池状态
     *
     * @param intent
     * @return
     */
    private String getStatus(Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        String statusStr = "";
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                // 正在充电
                statusStr = "正在充电";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                statusStr = "BATTERY_STATUS_DISCHARGING";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                // 充满
                statusStr = "充满";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                // 没有充电
                statusStr = "没有充电";
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                // 未知状态
                statusStr = "未知状态";
                break;
            default:
                break;
        }
        return statusStr;
    }

}
