package com.david_kapas.android.techtest.logic.api;

import com.david_kapas.android.techtest.logic.api.model.Comment;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Retro fit api interface for Comment endpoint.
 * Created by David Kapas on 2018.03.18.
 */

public interface CommentsApi {

    String BASE_URL = "http://jsonplaceholder.typicode.com";

    @GET("/comments")
    Observable<List<Comment>> getComments();
}
