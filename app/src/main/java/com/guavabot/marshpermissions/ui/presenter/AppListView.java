package com.guavabot.marshpermissions.ui.presenter;

import com.guavabot.marshpermissions.domain.entity.App;

import java.util.List;

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
     * Display this list of apps.
     */
    void setItems(List<App> apps);

    /**
     * Returns a filter for the list of apps to be displayed.
     */
    Observable<String> getPackageFilter();
}
