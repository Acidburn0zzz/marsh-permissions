package com.guavabot.marshpermissions.injection;

import com.guavabot.marshpermissions.domain.gateway.AppRepository;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;
import com.guavabot.marshpermissions.domain.interactor.GetAppListFilteredUseCase;
import com.guavabot.marshpermissions.domain.interactor.GetAppListUseCase;
import com.guavabot.marshpermissions.domain.interactor.ToggleAppHiddenUseCase;
import com.guavabot.marshpermissions.ui.presenter.AppListView;

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

    @Provides @ActivityScope
    GetAppListUseCase provideGetAppListUseCase(AppRepository appRepository, AppSettings appSettings) {
        return new GetAppListUseCase(appRepository, appSettings);
    }

    @Provides @ActivityScope
    GetAppListFilteredUseCase provideGetAppListFilteredUseCase(GetAppListUseCase getAppListUseCase) {
        return new GetAppListFilteredUseCase(getAppListUseCase);
    }

    @Provides @ActivityScope
    ToggleAppHiddenUseCase provideToggleAppHiddenUseCase(AppRepository appRepository) {
        return new ToggleAppHiddenUseCase(appRepository);
    }

}
