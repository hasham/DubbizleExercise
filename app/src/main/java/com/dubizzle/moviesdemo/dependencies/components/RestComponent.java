package com.dubizzle.moviesdemo.dependencies.components;

import android.app.Application;

import com.dubizzle.moviesdemo.dependencies.modules.AppModule;
import com.dubizzle.moviesdemo.dependencies.modules.NetModule;
import com.dubizzle.moviesdemo.ui.activities.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Developed by Hasham.Tahir on 1/5/2017.
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface RestComponent {
    void inject(BaseActivity activity);

    void inject(Application application);
}