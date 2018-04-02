package com.david_kapas.android.techtest.logic.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.david_kapas.android.techtest.logic.api.model.Post;
import com.david_kapas.android.techtest.logic.api.model.User;
import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;

import static com.david_kapas.android.techtest.logic.db.AndroidRoomDatabase.DB_VERSION;

/**
 * Database for app.
 * Created by David Kapas on 2018.03.17.
 */

@Database(entities = {Post.class, User.class}, version = DB_VERSION)
public abstract class AndroidRoomDatabase extends RoomDatabase {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "native_database";

    public abstract PostDao postDao();

    public abstract UserDao userDao();
}
