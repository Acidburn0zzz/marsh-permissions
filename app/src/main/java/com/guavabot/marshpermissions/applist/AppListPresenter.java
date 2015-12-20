package com.guavabot.marshpermissions.applist;

import android.content.SharedPreferences;

import com.guavabot.marshpermissions.model.App;
import com.guavabot.marshpermissions.model.AppRepository;
import com.guavabot.marshpermissions.settings.SettingsActivity;

import java.util.List;

import javax.inject.Inject;

/**
 * Presenter for a list of apps in an {@link AppListView}.
 */
public class AppListPresenter {

    private final AppListView mAppListView;
    private final AppRepository mAppRepository;
    private final SharedPreferences mPrefs;

    @Inject
    public AppListPresenter(AppListView appListView, AppRepository appRepository, SharedPreferences prefs) {
        mAppListView = appListView;
        mAppRepository = appRepository;
        mPrefs = prefs;
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
    public boolean isDisplayItemButton() {
        return mPrefs.getBoolean(SettingsActivity.PREF_HIDDEN, false);
    }

    private void findApps() {
        boolean displayHidden = mPrefs.getBoolean(SettingsActivity.PREF_HIDDEN, false);
        boolean displayGoogle = mPrefs.getBoolean(SettingsActivity.PREF_GOOGLE, false);
        boolean displayAndroid = mPrefs.getBoolean(SettingsActivity.PREF_ANDROID, false);
        List<App> apps = mAppRepository.findAppsMarshmallow(displayHidden, displayGoogle, displayAndroid);
        mAppListView.setItems(apps);
    }
}
