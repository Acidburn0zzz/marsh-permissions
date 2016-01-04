package com.guavabot.marshpermissions.injection;

import android.content.Context;

import com.guavabot.marshpermissions.Schedulers;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;
import com.guavabot.marshpermissions.ui.view.BaseActivity;

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
    AppSettings appSettings();
    Schedulers schedulers();
}
