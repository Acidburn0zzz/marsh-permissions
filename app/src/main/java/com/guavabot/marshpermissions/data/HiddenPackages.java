package com.guavabot.marshpermissions.data;

import android.support.annotation.NonNull;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;

import java.util.Collections;
import java.util.Set;

/**
 * Adapter for saving the set of packages hidden by the user in the SharedPreferences.
 */
class HiddenPackages {

    private static final String KEY_HIDDEN_APPS = "hidden";

    private final Preference<Set<String>> mHiddenAppsPref;

    HiddenPackages(RxSharedPreferences rxPrefs) {
        mHiddenAppsPref = rxPrefs.getStringSet(KEY_HIDDEN_APPS);
    }

    void set(Set<String> newHidden) {
        mHiddenAppsPref.set(newHidden);
    }

    @NonNull
    Set<String> get() {
        Set<String> packages = mHiddenAppsPref.get();
        return packages != null ? packages : Collections.<String>emptySet();
    }
}