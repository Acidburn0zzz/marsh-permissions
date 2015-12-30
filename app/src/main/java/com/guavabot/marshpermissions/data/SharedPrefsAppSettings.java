package com.guavabot.marshpermissions.data;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;

import rx.Observable;

/**
 * Retrieves the user settings from the SharedPreferences.
 */
public class SharedPrefsAppSettings implements AppSettings {

    private static final String PREF_HIDDEN = "pref_display_hidden";
    private static final String PREF_GOOGLE = "pref_display_google";
    private static final String PREF_ANDROID = "pref_display_android";

    private final RxSharedPreferences mRxPrefs;

    public SharedPrefsAppSettings(RxSharedPreferences rxPrefs) {
        mRxPrefs = rxPrefs;
    }

    @Override public Observable<Boolean> isDisplayHidden() {
        Preference<Boolean> displayPref = mRxPrefs.getBoolean(PREF_HIDDEN, false);
        return displayPref.asObservable();
    }

    @Override public Observable<Boolean> isDisplayGoogle() {
        Preference<Boolean> googlePref = mRxPrefs.getBoolean(PREF_GOOGLE, false);
        return googlePref.asObservable();
    }

    @Override public Observable<Boolean> isDisplayAndroid() {
        Preference<Boolean> androidPref = mRxPrefs.getBoolean(PREF_ANDROID, false);
        return androidPref.asObservable();
    }
}
