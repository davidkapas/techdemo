package com.david_kapas.android.techtest.logic.api

import com.david_kapas.android.techtest.logic.api.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Retro fit api interface for Post endpoint.
 * Created by David Kapas on 3/12/2019.
 */
const val POST_BASE_URL = "http://jsonplaceholder.typicode.com";

interface PostsApi {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>;
}