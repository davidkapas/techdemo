package com.david_kapas.android.techtest.di.components;

import android.app.Application;

import com.david_kapas.android.techtest.di.modules.ApiModule;
import com.david_kapas.android.techtest.di.modules.ApplicationModule;
import com.david_kapas.android.techtest.presentation.common.app.AndroidApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Injects application dependencies.
 * Created by David Kapas on 2018.03.17.
 */

@Singleton
@Component(modules = {ActivityBuilder.class, ApplicationModule.class, ApiModule.class, AndroidInjectionModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        ApplicationComponent build();
    }

    void inject(AndroidApplication androidApplication);

}
