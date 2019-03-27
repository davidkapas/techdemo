package com.david_kapas.android.techtest.logic.api

import com.david_kapas.android.techtest.logic.api.model.User
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Retro fit api interface for User endpoint.
 * Created by David Kapas on 3/12/2019.
 */
interface UsersApi {

    @GET("/users")
    fun getUsers(): Observable<List<User>>
}