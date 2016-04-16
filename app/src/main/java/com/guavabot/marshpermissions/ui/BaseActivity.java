package com.guavabot.marshpermissions.ui;

import android.support.v7.app.AppCompatActivity;

import com.guavabot.marshpermissions.AndroidApplication;
import com.guavabot.marshpermissions.injection.ApplicationComponent;

/**
 * Base for all activities to extend.
 */
public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Get the Main Application component for dependency injection.
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected abstract Presenter getPresenter();

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().onStop();
    }
}
