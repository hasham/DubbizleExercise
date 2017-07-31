package com.dubizzle.moviesdemo.data.api;


import com.dubizzle.moviesdemo.data.models.ApiResponse;

import retrofit2.Call;

/**
 * Developed by Hasham.Tahir on 10/6/2016.
 */

public interface RestCallBack {
    void onRestResponse(String action, Call<ApiResponse> call, ApiResponse response, Throwable t);
}
