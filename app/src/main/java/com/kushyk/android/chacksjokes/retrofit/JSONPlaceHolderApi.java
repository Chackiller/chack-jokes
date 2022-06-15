package com.kushyk.android.chacksjokes.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("/jokes/random")
    Call<JokesJSON> getRandomJokesJSON(@Query("category") String category);

    @GET("/jokes/categories")
    Call<List<String>> getCategoriesJSON();
}
