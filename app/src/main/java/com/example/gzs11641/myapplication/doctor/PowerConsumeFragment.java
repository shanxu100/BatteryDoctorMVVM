package com.example.gzs11641.myapplication.doctor;


import android.app.AppOpsManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.gzs11641.myapplication.R;
import com.example.gzs11641.myapplication.databinding.FragmentPowerConsumeBinding;
import com.example.gzs11641.myapplication.databinding.FragmentPowerMonitorBinding;
import com.example.gzs11641.myapplication.doctor.helper.CheckPermissionUtil;
import com.example.gzs11641.myapplication.doctor.helper.UBCAppUsageStats;
import com.example.gzs11641.myapplication.doctor.viewmodel.PowerConsumeViewModel;
import com.example.gzs11641.myapplication.doctor.viewmodel.PowerMonitorViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PowerConsumeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PowerConsumeFragment extends Fragment {

    private static final String TAG = "PowerConsumeFragment";

    public static final int REQUEST_CODE_APP_USAGE = 1;

    private PowerConsumeViewModel consumeViewModel;

    private FragmentPowerConsumeBinding mBinding;
    private ArrayAdapter<String> arrayAdapter;

    public PowerConsumeFragment() {
        // Required empty public constructor
    }

    public static PowerConsumeFragment newInstance() {
        PowerConsumeFragment fragment = new PowerConsumeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_power_consume, container, false);
        consumeViewModel = obtainViewModel(getActivity());
        checkPermission();
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1);
        mBinding.lvUsage.setAdapter(arrayAdapter);
        return mBinding.getRoot();
    }

    /**
     * @param activity
     * @return
     */
    public PowerConsumeViewModel obtainViewModel(FragmentActivity activity) {
        PowerConsumeViewModel viewModel = ViewModelProviders.of(activity).get(PowerConsumeViewModel.class);
        viewModel.usageEvent.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                refreshAppUsageList(strings);
            }
        });
        return viewModel;
    }

    private void checkPermission() {
        //api是在19新加入的，所以要注意加个判断
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            AppOpsManager appOps = (AppOpsManager) getContext().getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            mode = appOps.checkOpNoThrow("android:get_usage_stats", android.os.Process.myUid(), getContext().getPackageName());
            boolean granted = (mode == AppOpsManager.MODE_ALLOWED);
            if (!granted) {
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivityForResult(intent, REQUEST_CODE_APP_USAGE);
            } else {
                consumeViewModel.statsUsage();
            }
        }
    }

    private void refreshAppUsageList(List<String> strings) {
        arrayAdapter.addAll(strings);
        arrayAdapter.notifyDataSetChanged();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        consumeViewModel.handleActivityResult(requestCode, resultCode, data);
    }
}
