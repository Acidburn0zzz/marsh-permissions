package com.guavabot.marshpermissions.ui.app_list;

import com.guavabot.marshpermissions.injection.ActivityModule;
import com.guavabot.marshpermissions.injection.ActivityScope;
import com.guavabot.marshpermissions.injection.ApplicationComponent;

import dagger.Component;

/**
 * Component with the lifetime of the {@link AppListActivity}
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, AppListModule.class})
public interface AppListComponent {

    void inject(AppListActivity appListActivity);
}
