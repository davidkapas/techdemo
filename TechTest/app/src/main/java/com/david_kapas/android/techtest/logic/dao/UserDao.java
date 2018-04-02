package com.david_kapas.android.techtest.logic.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.david_kapas.android.techtest.logic.api.model.User;

import io.reactivex.Maybe;

/**
 * Dao for User.
 * Created by David Kapas on 2018.03.18.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User... users);

    @Query("SELECT * FROM User WHERE id =:userId ")
    Maybe<User> getUserById(int userId);
}
