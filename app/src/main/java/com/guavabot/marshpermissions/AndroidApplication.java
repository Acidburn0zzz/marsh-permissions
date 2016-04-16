package com.guavabot.marshpermissions;

import android.app.Application;

import com.guavabot.marshpermissions.injection.ApplicationComponent;
import com.guavabot.marshpermissions.injection.ApplicationModule;
import com.guavabot.marshpermissions.injection.DaggerApplicationComponent;
import com.guavabot.marshpermissions.ui.AppIconRequestHandler;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Main Application.
 */
public class AndroidApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        configPicasso();
        initializeInjector();
    }

    private void configPicasso() {
        OkHttpClient client = new OkHttpClient();
        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .addRequestHandler(new AppIconRequestHandler(this))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    private void initializeInjector() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}
