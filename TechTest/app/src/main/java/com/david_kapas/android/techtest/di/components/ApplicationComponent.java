package com.david_kapas.android.techtest.di.components;

import android.app.Application;
import android.content.res.Resources;

import com.david_kapas.android.techtest.di.modules.ApiModule;
import com.david_kapas.android.techtest.di.modules.ApplicationModule;
import com.david_kapas.android.techtest.logic.api.CommentsApi;
import com.david_kapas.android.techtest.logic.api.PostsApi;
import com.david_kapas.android.techtest.logic.api.UsersApi;
import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;
import com.david_kapas.android.techtest.presentation.common.app.AndroidApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Injects application dependencies.
 * Created by David Kapas on 2018.03.17.
 */

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    void inject(AndroidApplication androidApplication);

    PostsApi providePostApi();

    CommentsApi provideCommentsApi();

    UsersApi provideUsersApi();

    Application application();

    Resources resources();

    PostDao providePostDao();

    UserDao provideUserDao();

    /**
     * Inner injector class to avoid the boiler-plate dagger coding in injected class.
     */
    final class Injector {
        private static ApplicationComponent applicationComponent;

        private Injector() {

        }

        public static void inject(AndroidApplication androidApplication) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(androidApplication))
                    .build();
            applicationComponent.inject(androidApplication);
        }

        public static ApplicationComponent getComponent() {
            return applicationComponent;
        }
    }


}
