package com.guavabot.marshpermissions.ui.presenter;

import com.guavabot.marshpermissions.domain.entity.App;

import java.util.List;

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
     * Configure the item buttons to be hidden or not.
     */
    void setHideItemButtons(boolean hide);
}
