package com.david_kapas.android.techtest.presentation.common.app;

import android.app.Application;
import android.content.Context;

import com.david_kapas.android.techtest.di.components.ApplicationComponent;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * {@link Application} for the Android native app.
 * Created by David Kapas on 2018.03.17.
 */

public class AndroidApplication extends Application {

    private static volatile AndroidApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ApplicationComponent.Injector.inject(this);
        Fresco.initialize(this);
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }

}
