package com.guavabot.marshpermissions.applist;

import com.guavabot.marshpermissions.injection.PerActivity;

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

    @Provides @PerActivity
    AppListView provideAppListView() {
        return mAppListView;
    }

}
