package com.david_kapas.android.techtest.presentation.details.model

import com.david_kapas.android.techtest.logic.api.CommentsApi
import com.david_kapas.android.techtest.logic.api.model.Comment
import com.david_kapas.android.techtest.logic.api.model.Post
import com.david_kapas.android.techtest.logic.api.model.PostDetails
import com.david_kapas.android.techtest.logic.api.model.User
import com.david_kapas.android.techtest.logic.dao.PostDao
import com.david_kapas.android.techtest.logic.dao.UserDao
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

/**
 * Model class for post details.
 * Created by David Kapas on 3/26/2019.
 */
class PostDetailsModel(val commentsApi: CommentsApi, val postDao: PostDao, val userDao: UserDao) {

    fun showPostOverView(postId: Int): Maybe<PostDetails>? {
        var postSource: Maybe<Post> = postDao.getPostById(postId).subscribeOn(Schedulers.io());
        var userSource: Maybe<User> = postSource.flatMap { post: Post -> userDao.getUserById(post.userId) }.subscribeOn(Schedulers.io());
        return Maybe.zip(postSource, userSource, BiFunction<Post, User, PostDetails>() { post, user ->
            val postDetails = PostDetails()
            postDetails.postId = post.id!!
            postDetails.title = post.title
            postDetails.body = post.body
            postDetails.userName = user.name
            postDetails.userEmail = user.email
            postDetails
        })
    }

    fun getComments(): Observable<List<Comment>>? {
        return commentsApi.getComments().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}