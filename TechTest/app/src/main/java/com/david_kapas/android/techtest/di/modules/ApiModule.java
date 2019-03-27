package com.david_kapas.android.techtest.di.modules;

import com.david_kapas.android.techtest.logic.api.CommentsApi;
import com.david_kapas.android.techtest.logic.api.PostsApi;
import com.david_kapas.android.techtest.logic.api.UsersApi;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.david_kapas.android.techtest.logic.api.CommentsApiKt.BASE_URL;
import static com.david_kapas.android.techtest.logic.api.PostsApiKt.POST_BASE_URL;

/**
 * ApiModule for the provide any api dependency.
 * Created by David Kapas on 2018.03.17.
 */
@Module
public class ApiModule {


    @Provides
    @Singleton
    PostsApi providePostsApi(Converter.Factory converterFactory, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(POST_BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(PostsApi.class);
    }

    @Provides
    @Singleton
    CommentsApi provideCommentsApi(Converter.Factory converterFactory, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(CommentsApi.class);
    }

    @Provides
    @Singleton
    UsersApi provideUsersApi(Converter.Factory converterFactory, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(POST_BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(UsersApi.class);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    @Provides
    @Singleton
    Converter.Factory provideJacksonConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return JacksonConverterFactory.create(mapper);
    }
}
