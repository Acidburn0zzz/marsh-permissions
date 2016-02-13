package com.guavabot.marshpermissions.ui.presenter;

import rx.Observable;

/**
 * View that displays a list of apps.
 */
public interface AppListView {

    /**
     * Navigate to the Android application info screen for a package.
     */
    void startAppInfo(String packageName);

    /**
     * Returns a filter for the list of apps to be displayed.
     */
    Observable<String> getPackageFilter();
}
