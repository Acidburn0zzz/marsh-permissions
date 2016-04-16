package com.guavabot.marshpermissions.data;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;
import com.jakewharton.rxrelay.PublishRelay;

import java.util.ArrayList;
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
        Set<String> hiddenApps = mHiddenPackages.get();
        List<PackageInfo> infos = getPackageInfos();
        return Stream.of(infos)
                .filter(info -> info.applicationInfo.targetSdkVersion >= 23)
                .map(info -> {
                    CharSequence label = mPackageManager.getApplicationLabel(info.applicationInfo);
                    String appName = label != null ? label.toString() : null;
                    boolean hidden = hiddenApps.contains(info.packageName);
                    List<String> permissions = findGrantedPermissions(info);
                    return new App(info.packageName, appName, hidden, permissions);
                })
                .collect(Collectors.toList());
    }

    private List<PackageInfo> getPackageInfos() {
        return mPackageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
    }

    @NonNull
    private List<String> findGrantedPermissions(PackageInfo info) {
        List<String> permissions = new ArrayList<>();
        String[] requestedPermissions = info.requestedPermissions;
        if (requestedPermissions != null) {
            for (int i = 0; i < requestedPermissions.length; i++) {
                String permission = requestedPermissions[i];
                int permissionFlags = info.requestedPermissionsFlags[i];
                boolean granted = (permissionFlags & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0;
                if (granted) {
                    permissions.add(permission);
                }
            }
        }
        return permissions;
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
