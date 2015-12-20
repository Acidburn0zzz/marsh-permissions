package com.guavabot.marshpermissions;

import android.app.Application;

import com.guavabot.marshpermissions.injection.ApplicationComponent;
import com.guavabot.marshpermissions.injection.ApplicationModule;
import com.guavabot.marshpermissions.injection.DaggerApplicationComponent;

/**
 * Main Application.
 */
public class AndroidApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
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
