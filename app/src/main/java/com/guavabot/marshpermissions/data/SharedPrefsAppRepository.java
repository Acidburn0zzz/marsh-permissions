package com.guavabot.marshpermissions.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.functions.Func0;
import rx.subjects.PublishSubject;

/**
 * {@link AppRepository} that retrieves installed apps from the PackageManager and
 * stores the user-hidden apps in the SharedPreferences.
 */
public class SharedPrefsAppRepository implements AppRepository {

    private static final String KEY_HIDDEN_APPS = "hidden";

    private final Context mContext;
    private final SharedPreferences mPrefs;

    private final PublishSubject<Void> mUpdateSubject = PublishSubject.create();

    public SharedPrefsAppRepository(Context context, SharedPreferences prefs) {
        mContext = context;
        mPrefs = prefs;
    }

    @Override
    public Observable<List<App>> findAppsMarshmallow() {
        return Observable.defer(new Func0<Observable<List<App>>>() {
            @Override
            public Observable<List<App>> call() {
                return Observable.just(doFindAppsMarshmallow());
            }
        });
    }

    private List<App> doFindAppsMarshmallow() {
        List<App> apps = new ArrayList<>();

        Set<String> hidden = getHiddenPackages();
        List<ApplicationInfo> appInfos = getApplicationInfos();
        for (ApplicationInfo app : appInfos) {
            if (app.targetSdkVersion >= 23) {
                apps.add(new AppImpl(app.packageName, hidden.contains(app.packageName)));
            }
        }

        return apps;
    }

    private List<ApplicationInfo> getApplicationInfos() {
        PackageManager packageManager = mContext.getPackageManager();
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
    }

    @NonNull
    public Observable<Void> hiddenAppsUpdate() {
        return mUpdateSubject.asObservable();
    }

    @Override
    public Observable<Void> setAppHidden(final App app) {
        return Observable.defer(new Func0<Observable<Void>>() {
            @Override
            public Observable<Void> call() {
                doSetAppHidden(app);
                return Observable.just(null);
            }
        });
    }

    private void doSetAppHidden(App app) {
        Set<String> oldHidden = getHiddenPackages();
        if (!oldHidden.contains(app.getPackage())) {
            Set<String> newHidden = new HashSet<>(oldHidden);
            newHidden.add(app.getPackage());
            mPrefs.edit()
                    .putStringSet(KEY_HIDDEN_APPS, newHidden)
                    .apply();

            mUpdateSubject.onNext(null);
        }
    }

    @NonNull
    private Set<String> getHiddenPackages() {
        return mPrefs.getStringSet(KEY_HIDDEN_APPS, new HashSet<String>());
    }
}
