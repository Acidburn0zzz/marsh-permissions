package com.guavabot.marshpermissions.settings;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Retrieves the user settings from the SharedPreferences.
 */
public class AppSettings {

    private static final String PREF_HIDDEN = "pref_display_hidden";
    private static final String PREF_GOOGLE = "pref_display_google";
    private static final String PREF_ANDROID = "pref_display_android";

    private final SharedPreferences mPrefs;

    @Inject
    public AppSettings(SharedPreferences prefs) {
        mPrefs = prefs;
    }

    /**
     * Should apps hidden manually be displayed anyways?
     */
    public boolean isDisplayHidden() {
        return mPrefs.getBoolean(PREF_HIDDEN, false);
    }

    /**
     * Should apps hidden manually be displayed anyways?
     */
    public boolean isDisplayGoogle() {
        return mPrefs.getBoolean(PREF_GOOGLE, false);
    }

    /**
     * Should apps hidden manually be displayed anyways?
     */
    public boolean isDisplayAndroid() {
        return mPrefs.getBoolean(PREF_ANDROID, false);
    }
}
