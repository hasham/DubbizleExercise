package com.dubizzle.moviesdemo;

import android.app.Application;

import com.dubizzle.moviesdemo.dependencies.components.DaggerRestComponent;
import com.dubizzle.moviesdemo.dependencies.components.RestComponent;
import com.dubizzle.moviesdemo.dependencies.modules.AppModule;
import com.dubizzle.moviesdemo.dependencies.modules.NetModule;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Developed by hasham on 5/3/17.
 */

public class ApplicationMain extends Application {

    private RestComponent restComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        restComponent = DaggerRestComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(BuildConfig.HOST))
                .build();
    }

    public RestComponent getRestComponent() {
        return restComponent;
    }
}
