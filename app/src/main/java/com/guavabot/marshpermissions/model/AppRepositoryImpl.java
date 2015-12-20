package com.guavabot.marshpermissions.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

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

    @Inject
    public AppRepositoryImpl(Context context, SharedPreferences prefs) {
        mContext = context;
        mPrefs = prefs;
    }

    @Override
    public List<App> findAppsMarshmallow(boolean displayHidden, boolean google, boolean android) {
        List<App> apps = new ArrayList<>();

        PackageManager packageManager = mContext.getPackageManager();
        List<ApplicationInfo> appInfos = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo app : appInfos) {
            if (app.targetSdkVersion >= 23) {
                if ((google || !app.packageName.contains("com.google."))
                        && (android || !app.packageName.contains("com.android."))
                        && !app.packageName.contains("com.motorola.")) {
                    apps.add(new App(app.packageName));
                }
            }
        }

        if (!displayHidden) {
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
