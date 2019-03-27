package com.david_kapas.android.techtest.logic.api

import com.david_kapas.android.techtest.logic.api.model.Comment

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Retro fit api interface for Comment endpoint.
 * Created by David Kapas on 2018.03.18.
 */
const val BASE_URL = "http://jsonplaceholder.typicode.com"

interface CommentsApi {

    @GET("/comments")
    fun getComments(): Observable<List<Comment>>

}
