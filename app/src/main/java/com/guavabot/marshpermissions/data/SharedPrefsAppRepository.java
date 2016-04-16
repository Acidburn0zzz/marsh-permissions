package com.guavabot.marshpermissions.data;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;
import com.jakewharton.rxrelay.PublishRelay;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;

/**
 * {@link AppRepository} that retrieves installed apps from the PackageManager and
 * stores the user-hidden apps in the SharedPreferences.
 */
public class SharedPrefsAppRepository implements AppRepository {

    private final PackageManager mPackageManager;
    private final HiddenPackages mHiddenPackages;

    private final PublishRelay<Void> mUpdateSubject = PublishRelay.create();

    public SharedPrefsAppRepository(PackageManager packageManager, RxSharedPreferences rxPrefs) {
        this(packageManager, new HiddenPackages(rxPrefs));
    }

    public SharedPrefsAppRepository(PackageManager packageManager, HiddenPackages hiddenPackages) {
        mPackageManager = packageManager;
        mHiddenPackages = hiddenPackages;
    }

    @Override
    public Observable<List<App>> findAppsMarshmallow() {
        return Observable.fromCallable(() -> doFindAppsMarshmallow());
    }

    private List<App> doFindAppsMarshmallow() {
        Set<String> hidden = mHiddenPackages.get();
        List<ApplicationInfo> appInfos = getApplicationInfos();
        return Stream.of(appInfos)
                .filter(appInfo -> appInfo.targetSdkVersion >= 23)
                .map(appInfo -> {
                    CharSequence name = mPackageManager.getApplicationLabel(appInfo);
                    return new App(appInfo.packageName,
                            name != null ? name.toString() : null,
                            hidden.contains(appInfo.packageName));
                })
                .collect(Collectors.toList());
    }

    private List<ApplicationInfo> getApplicationInfos() {
        return mPackageManager.getInstalledApplications(PackageManager.GET_META_DATA);
    }

    @NonNull
    public Observable<Void> hiddenAppsUpdate() {
        return mUpdateSubject.asObservable();
    }

    @Override
    public Observable<Void> setAppHidden(final String appPackage) {
        return Observable.fromCallable(() -> {
            doSetAppHidden(appPackage);
            return null;
        });
    }

    private void doSetAppHidden(String appPackage) {
        Set<String> oldHidden = mHiddenPackages.get();
        if (!oldHidden.contains(appPackage)) {
            Set<String> newHidden = new HashSet<>(oldHidden);
            newHidden.add(appPackage);
            mHiddenPackages.set(newHidden);

            mUpdateSubject.call(null);
        }
    }

    @Override
    public Observable<Void> setAppNotHidden(final String appPackage) {
        return Observable.fromCallable(() -> {
            doSetAppNotHidden(appPackage);
            return null;
        });
    }

    private void doSetAppNotHidden(String appPackage) {
        Set<String> oldHidden = mHiddenPackages.get();
        if (oldHidden.contains(appPackage)) {
            Set<String> newHidden = new HashSet<>(oldHidden);
            newHidden.remove(appPackage);
            mHiddenPackages.set(newHidden);

            mUpdateSubject.call(null);
        }
    }
}
