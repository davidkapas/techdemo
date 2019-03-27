package com.david_kapas.android.techtest.presentation.details.model

import android.arch.lifecycle.MutableLiveData
import com.david_kapas.android.techtest.logic.api.CommentsApi
import com.david_kapas.android.techtest.logic.api.model.Comment
import com.david_kapas.android.techtest.logic.api.model.Post
import com.david_kapas.android.techtest.logic.api.model.PostDetails
import com.david_kapas.android.techtest.logic.api.model.User
import com.david_kapas.android.techtest.logic.dao.PostDao
import com.david_kapas.android.techtest.logic.dao.UserDao
import com.david_kapas.android.techtest.presentation.common.model.BaseModel
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Model class for post details.
 * Created by David Kapas on 3/26/2019.
 */
class PostDetailsModel(val commentsApi: CommentsApi, val postDao: PostDao, val userDao: UserDao) : BaseModel() {

    val commentListEntityMutableLiveData = MutableLiveData<CommentListEntity>()

    val postDetailsEntityMutableLiveData = MutableLiveData<PostDetailsEntity>()

    fun showPostOverView(postId: Int) {
        var postSource: Maybe<Post> = postDao.getPostById(postId).subscribeOn(Schedulers.io());
        var userSource: Maybe<User> = postSource.flatMap { post: Post -> userDao.getUserById(post.userId) }.subscribeOn(Schedulers.io());
        Maybe.zip(postSource, userSource, BiFunction<Post, User, PostDetails>() { post, user ->
            val postDetails = PostDetails()
            postDetails.postId = post.id!!
            postDetails.title = post.title
            postDetails.body = post.body
            postDetails.userName = user.name
            postDetails.userEmail = user.email
            postDetails
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer<PostDetails> { this.onPostAndUserData(it) }, Consumer<Throwable> { this.onPostAndUserError(it) })
    }

    fun getComments() {
        if (commentListEntityMutableLiveData.value == null) {
            commentsApi.getComments().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer<List<Comment>> { onResult(it) }, Consumer<Throwable> { this.onPostAndUserError(it) })
        }
    }

    private fun onPostAndUserData(postDetails: PostDetails) {
        postDetailsEntityMutableLiveData.value = PostDetailsEntity(postDetails)
    }

    private fun onPostAndUserError(throwable: Throwable) {
        val postDetailsEntity = PostDetailsEntity()
        postDetailsEntity.isError = true
        postDetailsEntityMutableLiveData.value = postDetailsEntity

    }

    private fun onResult(comments: List<Comment>) {
        commentListEntityMutableLiveData.value = CommentListEntity(comments)
    }

    private fun onError(throwable: Throwable) {
        val commentListEntity = CommentListEntity()
        commentListEntity.isError = true
        commentListEntityMutableLiveData.value = commentListEntity
    }

}