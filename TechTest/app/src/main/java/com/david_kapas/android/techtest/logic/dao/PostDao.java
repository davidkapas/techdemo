package com.david_kapas.android.techtest.logic.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.david_kapas.android.techtest.logic.api.model.Post;

import java.util.List;

import io.reactivex.Maybe;

/**
 * Dao for Post.
 * Created by David Kapas on 2018.03.17.
 */
@Dao
public interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPost(Post... posts);

    @Query("SELECT * FROM Post ")
    Maybe<List<Post>> getPostList();

    @Query("SELECT * FROM Post WHERE id =:postId ")
    Maybe<Post> getPostById(int postId);
}
