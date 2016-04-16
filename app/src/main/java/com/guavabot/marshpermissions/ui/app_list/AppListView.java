package com.guavabot.marshpermissions.ui.app_list;

import java.util.List;

import rx.Observable;

/**
 * View that displays a list of apps.
 */
public interface AppListView {

    /**
     * Display the list of apps.
     */
    void setApps(List<AppViewModel> apps);

    /**
     * Navigate to the Android application info screen for a package.
     */
    void startAppInfo(String packageName);

    /**
     * Returns a filter for the list of apps to be displayed or null if there's no search query.
     */
    Observable<String> getSearchQuery();
}
