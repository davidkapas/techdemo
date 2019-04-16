package com.david_kapas.android.techtest.presentation.posts.model

import com.annimon.stream.Stream
import com.david_kapas.android.techtest.logic.api.PostsApi
import com.david_kapas.android.techtest.logic.api.UsersApi
import com.david_kapas.android.techtest.logic.api.model.Post
import com.david_kapas.android.techtest.logic.api.model.PostAndUser
import com.david_kapas.android.techtest.logic.api.model.User
import com.david_kapas.android.techtest.logic.dao.PostDao
import com.david_kapas.android.techtest.logic.dao.UserDao
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Model class for the list of posts.
 * Created by David Kapas on 3/14/2019.
 */
class PostListModel(val postsApi: PostsApi, val postDao: PostDao, val usersApi: UsersApi, val userDao: UserDao) {


    var postObservable: Observable<List<Post>>? = null
    var userObservable: Observable<List<User>>? = null

    fun getPosts(): Observable<List<PostAndUser>>? {
        postObservable = postsApi.getPosts().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).doAfterNext(this::storePostNetworkResponseInDb);
        userObservable = usersApi.getUsers().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).doAfterNext(this::storeUserNetworkResponseInDb);
        return getPostsAndUsers();
    }


    private fun getPostsAndUsers(): Observable<List<PostAndUser>>? {
        return Observable.zip(postObservable, userObservable, BiFunction<List<Post>, List<User>, List<PostAndUser>>() { posts, users ->
            var list = ArrayList<PostAndUser>()
            for (post in posts) {
                var currentUser = Stream.of<User>(users).filter({ user -> user.id == post.userId }).findFirst().orElse(User())
                list.add(PostAndUser(post, currentUser))
            }
            list;
        });
    }

    private fun storePostNetworkResponseInDb(posts: List<Post>) {
        Single.fromCallable {
            Stream.ofNullable(posts).forEach { post -> postDao.insertPost(post) }
            true
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer<Boolean> { onNext(it) }, Consumer<Throwable> { onDBError(it) });
    }

    private fun storeUserNetworkResponseInDb(users: List<User>) {
        Single.fromCallable {
            Stream.ofNullable(users).forEach { user -> userDao.insertUser(user) }
            true
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(Consumer<Boolean> { onNext(it) }, Consumer<Throwable> { onDBError(it) })
    }

    private fun onNext(boolean: Boolean) {

    }

    private fun onDBError(throwable: Throwable) {
        // the extended error handling not implemented due to short time frame
    }
}