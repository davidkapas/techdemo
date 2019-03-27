package com.david_kapas.android.techtest.logic.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.david_kapas.android.techtest.logic.api.model.Post
import io.reactivex.Maybe

/**
 * Dao for Post.
 * Created by David Kapas on 3/12/2019.
 */
@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)

    @Query("SELECT * FROM Post ")
    fun getPostList(): Maybe<List<Post>>

    @Query("SELECT * FROM Post WHERE id =:postId ")
    fun getPostById(postId: Int): Maybe<Post>
}