package com.guavabot.marshpermissions.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.guavabot.marshpermissions.settings.AppSettings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * {@link AppRepository} that retrieves installed apps from the PackageManager and
 * stores the user-hidden apps in the SharedPreferences.
 */
public class AppRepositoryImpl implements AppRepository {

    private static final String KEY_HIDDEN_APPS = "hidden";

    private final Context mContext;
    private final SharedPreferences mPrefs;
    private final AppSettings mAppSettings;

    @Inject
    public AppRepositoryImpl(Context context, SharedPreferences prefs, AppSettings appSettings) {
        mContext = context;
        mPrefs = prefs;
        mAppSettings = appSettings;
    }

    @Override
    public List<App> findAppsMarshmallow() {
        List<App> apps = new ArrayList<>();

        PackageManager packageManager = mContext.getPackageManager();
        List<ApplicationInfo> appInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        boolean google = mAppSettings.isDisplayGoogle();
        boolean android = mAppSettings.isDisplayAndroid();
        for (ApplicationInfo app : appInfos) {
            if (app.targetSdkVersion >= 23) {
                if ((google || !app.packageName.contains("com.google."))
                        && (android || !app.packageName.contains("com.android."))
                        && !app.packageName.contains("com.motorola.")) {
                    apps.add(new App(app.packageName));
                }
            }
        }

        if (!mAppSettings.isDisplayHidden()) {
            Set<String> hidden = getHiddenPackages();
            Iterator<App> it = apps.iterator();
            while (it.hasNext()) {
                if (hidden.contains(it.next().getPackage())) {
                    it.remove();
                }
            }
        }

        return apps;
    }

    @Override
    public void setAppHidden(App app) {
        Set<String> oldHidden = getHiddenPackages();
        Set<String> newHidden = new HashSet<>(oldHidden);
        newHidden.add(app.getPackage());
        mPrefs.edit()
                .putStringSet(KEY_HIDDEN_APPS, newHidden)
                .apply();
    }

    @NonNull
    private Set<String> getHiddenPackages() {
        return mPrefs.getStringSet(KEY_HIDDEN_APPS, new HashSet<String>());
    }
}
