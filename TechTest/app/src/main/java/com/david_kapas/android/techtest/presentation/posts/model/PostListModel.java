package com.david_kapas.android.techtest.presentation.posts.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.annimon.stream.Stream;
import com.david_kapas.android.techtest.logic.api.PostsApi;
import com.david_kapas.android.techtest.logic.api.UsersApi;
import com.david_kapas.android.techtest.logic.api.model.Post;
import com.david_kapas.android.techtest.logic.api.model.PostAndUser;
import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;
import com.david_kapas.android.techtest.logic.api.model.User;
import com.david_kapas.android.techtest.presentation.common.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Model class for the list of posts.
 * Created by David Kapas on 2018.03.17.
 */

public class PostListModel extends BaseModel {
    private static final String TAG = PostListModel.class.getSimpleName();

    private PostsApi postsApi;
    private PostDao postDao;
    private UsersApi usersApi;
    private UserDao userDao;

    private MutableLiveData<PostAndUserEntity> postAndUserEntityMutableLiveData = new MutableLiveData<>();

    public PostListModel(PostsApi api, PostDao postDao, UsersApi usersApi, UserDao userDao) {
        this.postsApi = api;
        this.postDao = postDao;
        this.usersApi = usersApi;
        this.userDao = userDao;
    }

    public void getPosts(boolean forceUpdate) {
        if (postAndUserEntityMutableLiveData.getValue() == null || forceUpdate) {
            // the proper repository pattern not implemented due to short time frame, although the model class would be more testable
            Observable<List<Post>> postObservable = postsApi.getPosts().subscribeOn(Schedulers.newThread())
                    .doAfterNext(this::storePostNetworkResponseInDb);
            Observable<List<User>> userObservable = usersApi.getUsers().subscribeOn(Schedulers.newThread())
                    .doAfterNext(this::storeUserNetworkResponseInDb);
            getPostsAndUsers(postObservable, userObservable);
        }
    }

    private void getPostsAndUsers(Observable<List<Post>> postObservable, Observable<List<User>> userObservable) {
        Observable.zip(postObservable, userObservable, (posts, users) -> {
            List<PostAndUser> list = new ArrayList<>();
            for (Post post : posts) {
                User currentUser = Stream.of(users).filter(user -> user.getId() == post.getUserId()).findFirst().orElse(new User());
                list.add(new PostAndUser(post, currentUser));
            }
            return list;
        }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread()).subscribe(this::onResult, this::onError);
    }

    private void onError(Throwable throwable) {
        PostAndUserEntity postAndUserEntity = new PostAndUserEntity();
        postAndUserEntity.setError(true);
        postAndUserEntityMutableLiveData.setValue(postAndUserEntity);
    }

    private void onResult(List<PostAndUser> postAndUsers) {
        postAndUserEntityMutableLiveData.setValue(new PostAndUserEntity(postAndUsers));
    }

    public LiveData<PostAndUserEntity> getPostAndUserEntityMutableLiveData() {
        return postAndUserEntityMutableLiveData;
    }

    private void storePostNetworkResponseInDb(List<Post> posts) {
        Observable.fromCallable(() -> {
            Stream.ofNullable(posts).forEach(post -> postDao.insertPost(post));
            return true;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onNext, this::onDBError);
    }

    private void storeUserNetworkResponseInDb(List<User> users) {
        Observable.fromCallable(() -> {
            Stream.ofNullable(users).forEach(user -> userDao.insertUser(user));
            return true;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::onNext, this::onDBError);
    }

    private void onDBError(Throwable throwable) {
        // the extended error handling not implemented due to short time frame
    }

    private void onNext(Boolean aBoolean) {

    }

}
