package com.guavabot.marshpermissions.injection;

import android.content.Context;
import android.content.SharedPreferences;

import com.guavabot.marshpermissions.BaseActivity;
import com.guavabot.marshpermissions.model.AppRepository;
import com.guavabot.marshpermissions.settings.AppSettings;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    Context context();
    AppRepository appRepository();
    SharedPreferences sharedPreferences();
    AppSettings appSettings();
}
