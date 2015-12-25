package com.guavabot.marshpermissions.applist;

import com.guavabot.marshpermissions.injection.ActivityScope;
import com.guavabot.marshpermissions.model.App;
import com.guavabot.marshpermissions.model.AppRepository;
import com.guavabot.marshpermissions.settings.AppSettings;

import java.util.List;

import javax.inject.Inject;

/**
 * Presenter for a list of apps in an {@link AppListView}.
 */
@ActivityScope
public class AppListPresenter {

    private final AppListView mAppListView;
    private final AppRepository mAppRepository;
    private final AppSettings mAppSettings;

    @Inject
    public AppListPresenter(AppListView appListView, AppRepository appRepository, AppSettings appSettings) {
        mAppListView = appListView;
        mAppRepository = appRepository;
        mAppSettings = appSettings;
    }

    /**
     * Load the list of items.
     */
    public void load() {
        findApps();
    }

    /**
     * An app item was clicked.
     */
    public void onItemClicked(App app) {
        mAppListView.startAppInfo(app.getPackage());
    }

    /**
     * The button for an item was clicked.
     */
    public void onItemButtonClicked(App app) {
        mAppRepository.setAppHidden(app);
        findApps();
    }

    /**
     * Should the button for each item be displayed?
     */
    public boolean isHideItemButton() {
        return mAppSettings.isDisplayHidden();
    }

    private void findApps() {
        List<App> apps = mAppRepository.findAppsMarshmallow();
        mAppListView.setItems(apps);
    }
}
