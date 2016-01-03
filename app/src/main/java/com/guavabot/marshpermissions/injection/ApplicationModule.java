package com.guavabot.marshpermissions.injection;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.guavabot.marshpermissions.AndroidApplication;
import com.guavabot.marshpermissions.data.SharedPrefsAppRepository;
import com.guavabot.marshpermissions.data.SharedPrefsAppSettings;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {

    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides @Singleton
    AppRepository provideUserRepository(Context context, RxSharedPreferences rxPrefs) {
        return new SharedPrefsAppRepository(context.getPackageManager(), rxPrefs);
    }

    @Provides @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides @Singleton
    RxSharedPreferences provideRxPreferences(SharedPreferences prefs) {
        return RxSharedPreferences.create(prefs);
    }

    @Provides @Singleton
    AppSettings provideAppSettings(RxSharedPreferences rxPrefs) {
        return new SharedPrefsAppSettings(rxPrefs);
    }
}
