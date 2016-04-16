package com.guavabot.marshpermissions.ui.app_list;

import com.guavabot.marshpermissions.domain.gateway.AppRepository;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;
import com.guavabot.marshpermissions.domain.interactor.GetAppListFilteredUseCase;
import com.guavabot.marshpermissions.domain.interactor.GetAppListUseCase;
import com.guavabot.marshpermissions.domain.interactor.ToggleAppHiddenUseCase;
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

    @Provides @ComponentScope
    GetAppListUseCase provideGetAppListUseCase(AppRepository appRepository, AppSettings appSettings) {
        return new GetAppListUseCase(appRepository, appSettings);
    }

    @Provides @ComponentScope
    GetAppListFilteredUseCase provideGetAppListFilteredUseCase(GetAppListUseCase getAppListUseCase) {
        return new GetAppListFilteredUseCase(getAppListUseCase);
    }

    @Provides @ComponentScope
    ToggleAppHiddenUseCase provideToggleAppHiddenUseCase(AppRepository appRepository) {
        return new ToggleAppHiddenUseCase(appRepository);
    }

}
