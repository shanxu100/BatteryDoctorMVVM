package com.example.gzs11641.myapplication.doctor.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.databinding.ObservableFloat;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import java.util.Observable;

public class PowerMonitorViewModel extends AndroidViewModel {

    public final ObservableLong now = new ObservableLong();
    public final ObservableFloat level = new ObservableFloat();
    public final ObservableFloat temp = new ObservableFloat();
    public final ObservableFloat voltage = new ObservableFloat();
    public final ObservableField<String> health = new ObservableField<String>();
    public final ObservableField<String> status = new ObservableField<String>();


    public final MediatorLiveData<Void> updateEvent=new MediatorLiveData<>();


    public PowerMonitorViewModel(@NonNull Application application) {
        super(application);
    }


}
