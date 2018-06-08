package com.example.gzs11641.myapplication.doctor;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.gzs11641.myapplication.R;

public class BatteryDoctorActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    private PowerMonitorFragment monitorFragment = PowerMonitorFragment.newInstance();
    private PowerConsumeFragment consumeFragment = PowerConsumeFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_doctor);
        setupToolbar();
        setupNavigation();
        setupFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //弹出navigation bar
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void setupNavigation() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.nav_menu_1:
                                    changeFragment(monitorFragment);
                                    break;
                                case R.id.nav_menu_2:
                                    changeFragment(consumeFragment);
                                    break;
                                default:
                                    break;
                            }
                            // Close the navigation drawer when an item is selected.
                            menuItem.setChecked(true);
                            mDrawerLayout.closeDrawers();
                            return true;
                        }
                    });
        }
    }

    private void setupFragment() {
        Fragment showFragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (showFragment == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.contentFrame, monitorFragment).show(monitorFragment).commit();
        }
    }

    /**
     * fragment的切换
     */
    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment showFragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (showFragment != fragment) {
            //这里用replace，没有用add和hide和show。使得Fragment重新走一遍生命周期
            transaction.replace(R.id.contentFrame, fragment).commit();
            Toast.makeText(mContext, "切换Fragment", Toast.LENGTH_SHORT).show();
        }

    }



}
