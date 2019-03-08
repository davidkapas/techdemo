package com.david_kapas.android.techtest.presentation.common.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.david_kapas.android.techtest.di.components.DaggerApplicationComponent;
import com.facebook.drawee.backends.pipeline.Fresco;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * {@link Application} for the Android native app.
 * Created by David Kapas on 2018.03.17.
 */

public class AndroidApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private static volatile AndroidApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
        Fresco.initialize(this);
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

}
