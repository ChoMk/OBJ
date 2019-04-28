package com.mksoft.obj.Component;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;


import com.mksoft.obj.DI.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;


public class App extends Application implements HasActivityInjector{
    public static Context context;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;


    @Override
    public void onCreate() {
        super.onCreate();
        this.initDagger();
        context = getApplicationContext();

    }



    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }



    private void initDagger(){
        DaggerAppComponent.builder().application(this).build().inject(this);
    }


}
