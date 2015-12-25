package com.guavabot.marshpermissions.applist;

import com.guavabot.marshpermissions.injection.ActivityScope;

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

    @Provides @ActivityScope
    AppListView provideAppListView() {
        return mAppListView;
    }

}
