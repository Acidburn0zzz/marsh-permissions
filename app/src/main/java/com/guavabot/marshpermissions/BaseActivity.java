package com.guavabot.marshpermissions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guavabot.marshpermissions.injection.ActivityModule;
import com.guavabot.marshpermissions.injection.ApplicationComponent;

/**
 * Base for all activities to extend.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
    }

    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
