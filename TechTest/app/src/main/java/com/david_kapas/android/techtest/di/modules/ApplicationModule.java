package com.david_kapas.android.techtest.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.res.Resources;

import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;
import com.david_kapas.android.techtest.logic.db.AndroidRoomDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Provides application-wide dependencies.
 * Created by David Kapas on 2018.03.17.
 */
@Module
@Singleton
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return application.getResources();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }

    @Provides
    @Singleton
    AndroidRoomDatabase provideDB(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(), AndroidRoomDatabase.class, AndroidRoomDatabase.DB_NAME).build();
    }

    @Provides
    @Singleton
    PostDao providePostDao(AndroidRoomDatabase db) {
        return db.postDao();
    }

    @Provides
    @Singleton
    UserDao provideUserDao(AndroidRoomDatabase db) {
        return db.userDao();
    }

}
