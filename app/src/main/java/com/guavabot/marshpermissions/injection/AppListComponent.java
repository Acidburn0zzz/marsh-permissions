package com.guavabot.marshpermissions.injection;

import com.guavabot.marshpermissions.ui.view.AppListActivity;

import dagger.Component;

/**
 * Component with the lifetime of the {@link AppListActivity}
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, AppListModule.class})
public interface AppListComponent {

    void inject(AppListActivity appListActivity);
}
