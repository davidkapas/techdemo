package com.david_kapas.android.techtest.presentation.posts.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.MutableLiveData;

import com.david_kapas.android.techtest.logic.api.model.PostAndUser;
import com.david_kapas.android.techtest.logic.api.model.User;
import com.david_kapas.android.techtest.presentation.posts.model.PostAndUserEntity;
import com.david_kapas.android.techtest.presentation.posts.router.PostListRouter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import javax.inject.Provider;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link PostsViewModel}.
 * Created by David_Kapas on 3/19/2018.
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class PostsViewModelTest {

    private static final Post POST = new Post();
    private static final User USER = new User();
    private static final PostAndUser POST_AND_USER_1 = new PostAndUser(POST, USER);
    private static final PostAndUser POST_AND_USER_2 = new PostAndUser(POST, USER);
    private static final int POST_ID_1 = 1;
    private static final int POST_ID_2 = 2;

    @Mock
    private PostListModel modelMock;
    @Mock
    private PostListRouter routerMock;

    @Mock
    private Provider<PostListItemViewModel> postListItemViewModelProvider;

    private PostsViewModel underTest;

    private MutableLiveData<PostAndUserEntity> liveData = new MutableLiveData<>();

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        LifecycleRegistry lifecycle = new LifecycleRegistry(routerMock);
        lifecycle.markState(Lifecycle.State.RESUMED);
        when(postListItemViewModelProvider.get()).thenReturn(new PostListItemViewModel(routerMock));
        when(modelMock.getPostAndUserEntityMutableLiveData()).thenReturn(liveData);
        when(routerMock.getLifecycle()).thenReturn(lifecycle);

        underTest = new PostsViewModel(modelMock, routerMock, postListItemViewModelProvider);
    }

    @Test
    public void testLoadAllPosts() {
        // WHEN
        underTest.getAllPosts();

        // THEN
        verify(modelMock).getPosts();
    }

    @Test
    public void testOnGetAllPostsSuccess() {
        //GIVEN
        POST_AND_USER_1.getPost().setId(POST_ID_1);
        POST_AND_USER_2.getPost().setId(POST_ID_2);
        List<PostAndUser> postAndUsers = Arrays.asList(POST_AND_USER_1, POST_AND_USER_2);
        PostListItemViewModel postListItemViewModel = new PostListItemViewModel(routerMock);
        PostListItemViewModel postListItemViewModel_2 = new PostListItemViewModel(routerMock);

        // EXPECT
        when(postListItemViewModelProvider.get())
                .thenReturn(postListItemViewModel).thenReturn(postListItemViewModel_2);

        // WHEN
        liveData.setValue(new PostAndUserEntity(postAndUsers));

        // THEN
        for (int i = 0; i < postAndUsers.size(); i++) {
            PostListItemViewModel itemViewModel = (PostListItemViewModel) underTest.getPostList().get(i);
            PostAndUser postAndUser = postAndUsers.get(i);
            assertTrue(itemViewModel.getPost().getId() == postAndUser.getPost().getId());
        }
    }

    @Test
    public void testOnGetAllPostsError() {
        PostAndUserEntity postAndUser = new PostAndUserEntity();
        postAndUser.setError(true);

        liveData.setValue(postAndUser);

        verify(routerMock).showGeneralErrorDialog();
    }

}
