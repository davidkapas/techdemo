package com.david_kapas.android.techtest.presentation.posts.model;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.david_kapas.android.techtest.logic.api.PostsApi;
import com.david_kapas.android.techtest.logic.api.UsersApi;
import com.david_kapas.android.techtest.logic.api.model.Post;
import com.david_kapas.android.techtest.logic.api.model.User;
import com.david_kapas.android.techtest.logic.dao.PostDao;
import com.david_kapas.android.techtest.logic.dao.UserDao;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

/**
 * Unit test for {@link PostListModel}.
 * Created by David_Kapas on 3/19/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostListModelTest {

    private static final int POST_ID_1 = 1;
    private static final int POST_ID_2 = 2;
    private static final int USER_ID_1 = 1;
    private static final int USER_ID_2 = 2;
    private static final String POST_TITLE = "Post test title ";
    private static final String POST_BODY = "Post test body ";
    private static final Post POST_1 = new Post();
    private static final Post POST_2 = new Post();
    private static final User USER_1 = new User();
    private static final User USER_2 = new User();
    private static final String USER_1_EMAIL = "user1@test.com";
    private static final String USER_2_EMAIL = "user2@test.com";
    private static final String USER_1_NAME = "Test User1";
    private static final String USER_2_NAME = "Test User2";

    @Mock
    private PostsApi postsApi;
    @Mock
    private PostDao postDao;
    @Mock
    private UsersApi usersApi;
    @Mock
    private UserDao userDao;

    private PostListModel underTest;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @BeforeClass
    public static void setUpSuite() {
        RxJavaPlugins.setIoSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulers -> Schedulers.trampoline());
    }

    @AfterClass
    public static void tearDownSuite() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PostListModel(postsApi, postDao, usersApi, userDao);
    }

    @Test
    public void getAllPostSuccess() {

        //GIVEN
        POST_1.setUserId(USER_ID_1);
        POST_1.setId(POST_ID_1);
        POST_1.setTitle(POST_TITLE + POST_ID_1);
        POST_1.setBody(POST_BODY + POST_ID_1);
        POST_2.setUserId(USER_ID_2);
        POST_2.setId(POST_ID_2);
        POST_2.setTitle(POST_TITLE + POST_ID_2);
        POST_2.setBody(POST_BODY + POST_ID_2);
        USER_1.setEmail(USER_1_EMAIL);
        USER_1.setId(USER_ID_1);
        USER_1.setName(USER_1_NAME);
        USER_2.setEmail(USER_2_EMAIL);
        USER_2.setId(USER_ID_2);
        USER_2.setName(USER_2_NAME);
        List<Post> postResponse = new ArrayList<>();
        postResponse.add(POST_1);
        postResponse.add(POST_2);
        List<User> userResponse = new ArrayList<>();
        userResponse.add(USER_1);
        userResponse.add(USER_2);
        Observable<List<Post>> postObservable = Observable.just(postResponse);
        Observable<List<User>> userObservable = Observable.just(userResponse);

        //EXPECT
        Mockito.when(postsApi.getPosts()).thenReturn(postObservable);
        Mockito.when(usersApi.getUsers()).thenReturn(userObservable);

        //WHEN
        underTest.getPosts();

        // THEN
        PostAndUserEntity postAndUserEntity = underTest.getPostAndUserEntityMutableLiveData().getValue();

        Assert.assertEquals(false, postAndUserEntity.isError());
        Assert.assertEquals(2, postAndUserEntity.getPostAndUsers().size());
        Assert.assertEquals(Integer.valueOf(POST_ID_1), postAndUserEntity.getPostAndUsers().get(0).getPost().getId());
        Assert.assertEquals(USER_1_EMAIL, postAndUserEntity.getPostAndUsers().get(0).getUser().getEmail());
        Assert.assertEquals(USER_2_EMAIL, postAndUserEntity.getPostAndUsers().get(1).getUser().getEmail());
        Assert.assertEquals(POST_TITLE + POST_ID_2, postAndUserEntity.getPostAndUsers().get(1).getPost().getTitle());

    }

    @Test
    public void getAllPostError() {

        //GIVEN
        USER_1.setEmail(USER_1_EMAIL);
        USER_1.setId(USER_ID_1);
        USER_1.setName(USER_1_NAME);
        USER_2.setEmail(USER_2_EMAIL);
        USER_2.setId(USER_ID_2);
        USER_2.setName(USER_2_NAME);
        List<User> userResponse = new ArrayList<>();
        userResponse.add(USER_1);
        userResponse.add(USER_2);
        Throwable throwable = new Throwable();
        Observable<List<Post>> postObservable = Observable.error(throwable);
        Observable<List<User>> userObservable = Observable.just(userResponse);

        //EXPECT
        Mockito.when(postsApi.getPosts()).thenReturn(postObservable);
        Mockito.when(usersApi.getUsers()).thenReturn(userObservable);

        //WHEN
        underTest.getPosts();

        // THEN
        PostAndUserEntity postAndUserEntity = underTest.getPostAndUserEntityMutableLiveData().getValue();

        Assert.assertEquals(true, postAndUserEntity.isError());
    }
}
