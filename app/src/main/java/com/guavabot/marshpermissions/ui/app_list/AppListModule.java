package com.guavabot.marshpermissions.ui.app_list;

import com.guavabot.marshpermissions.injection.ComponentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Provides dependencies for the AppList.
 */
@Module
public class AppListModule {

    private final AppListView mAppListView;

    public AppListModule(AppListView appListView) {
        mAppListView = appListView;
    }

    @Provides @ComponentScope
    AppListView provideAppListView() {
        return mAppListView;
    }
}
