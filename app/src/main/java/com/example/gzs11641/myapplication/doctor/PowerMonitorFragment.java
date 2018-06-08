package com.example.gzs11641.myapplication.doctor;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gzs11641.myapplication.R;
import com.example.gzs11641.myapplication.databinding.FragmentPowerMonitorBinding;
import com.example.gzs11641.myapplication.doctor.viewmodel.PowerMonitorViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PowerMonitorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PowerMonitorFragment extends Fragment {

    /**
     * ayout文件自动生成一个默认的Binding类，类名是根据layout文件名生成的
     */
    private FragmentPowerMonitorBinding mBinding;

    private PowerMonitorViewModel monitorViewModel;

    private BatteryChangedReceiver batteryChangedReceiver;


    private static final String TAG = "PowerMonitorFragment";


    public static PowerMonitorFragment newInstance() {
        PowerMonitorFragment fragment = new PowerMonitorFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_power_monitor, container, false);
        monitorViewModel = obtainViewModel(getActivity());
        batteryChangedReceiver = new BatteryChangedReceiver(monitorViewModel);
        mBinding.setViewmodel(monitorViewModel);
        registerBattery();
        monitorViewModel.updateEvent.observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                Toast.makeText(getContext(), "更新电量显示", Toast.LENGTH_SHORT).show();
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unRegisterBattery();
    }

    /**
     * @param activity
     * @return
     */
    public PowerMonitorViewModel obtainViewModel(FragmentActivity activity) {
        PowerMonitorViewModel viewModel = ViewModelProviders.of(activity).get(PowerMonitorViewModel.class);
        return viewModel;
    }

    /**
     * 注册电量监听的Broadcastreceiver
     */
    public void registerBattery() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        getActivity().registerReceiver(batteryChangedReceiver, intentFilter);
        Log.e(TAG, "电量变化的receiver已经注册成功");
    }

    /**
     * 取消注册电量监听的Broadcastreceiver
     */
    public void unRegisterBattery() {
        getActivity().unregisterReceiver(batteryChangedReceiver);
        Log.e(TAG, "电量变化的receiver已经取消注册");

    }

}
