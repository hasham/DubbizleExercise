package com.dubizzle.moviesdemo.data.api;


import com.dubizzle.moviesdemo.data.models.ApiResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 * Developed by Hasham.Tahir on 10/6/2016.
 */

public interface ApiEndpoints {

    @Headers("Content-Type: application/json")
    @GET(API.ACTION_DISCOVER)
    Call<ApiResponse> getMovies(@QueryMap Map<String, String> params);

}
