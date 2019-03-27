package com.david_kapas.android.techtest.logic.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.david_kapas.android.techtest.logic.api.model.User
import io.reactivex.Maybe

/**
 * Dao for User.
 * Created by David Kapas on 3/12/2019.
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User);

    @Query("SELECT * FROM User WHERE id =:userId ")
    fun getUserById(userId: Int): Maybe<User>;
}