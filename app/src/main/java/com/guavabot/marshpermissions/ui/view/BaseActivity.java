package com.guavabot.marshpermissions.ui.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guavabot.marshpermissions.AndroidApplication;
import com.guavabot.marshpermissions.injection.ActivityModule;
import com.guavabot.marshpermissions.injection.ApplicationComponent;
import com.guavabot.marshpermissions.ui.presenter.Presenter;

/**
 * Base for all activities to extend.
 */
public abstract class BaseActivity extends AppCompatActivity {

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
