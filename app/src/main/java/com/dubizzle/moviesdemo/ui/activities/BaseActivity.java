package com.dubizzle.moviesdemo.ui.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.dubizzle.moviesdemo.ApplicationMain;
import com.dubizzle.moviesdemo.R;
import com.dubizzle.moviesdemo.data.api.ApiEndpoints;
import com.dubizzle.moviesdemo.data.api.RestCallBack;
import com.dubizzle.moviesdemo.data.models.ApiResponse;
import com.dubizzle.moviesdemo.util.HAlert;

import java.net.ConnectException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Developed by hasham on 7/31/17.
 */

public class BaseActivity extends AppCompatActivity {

    public static final String NO_INTERNET = "NO_INTERNET";
    @Inject
    Retrofit retrofit;
    public ApiEndpoints apiService;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ApplicationMain) getApplication()).getRestComponent().inject(this);

        apiService = retrofit.create(ApiEndpoints.class);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void apiCall(Call<ApiResponse> call, final String callbackAction, final RestCallBack restCallBack) {

        if (isConnectingToInternet()) {

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    try {

                        restCallBack.onRestResponse(callbackAction, call, response.body(), null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {

                    if (t instanceof java.net.SocketTimeoutException || t instanceof ConnectException) {
                        HAlert.showToast(BaseActivity.this, getString(R.string.cannotConnect));
                    }

                    restCallBack.onRestResponse(callbackAction, call, null, t);
                }
            });
        } else {
            HAlert.showToast(BaseActivity.this, getString(R.string.cannotConnect));
            restCallBack.onRestResponse(callbackAction, call, null, new Throwable(NO_INTERNET));
        }

    }

    private boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnected();
        } else {
            return false;
        }
    }
}
