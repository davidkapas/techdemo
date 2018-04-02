package com.david_kapas.android.techtest.logic.api;

import com.david_kapas.android.techtest.logic.api.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Retro fit api interface for User endpoint.
 * Created by David Kapas on 2018.03.18.
 */

public interface UsersApi {

    String BASE_URL = "http://jsonplaceholder.typicode.com";

    @GET("/users")
    Observable<List<User>> getUsers();

}
