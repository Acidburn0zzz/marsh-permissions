package com.guavabot.marshpermissions.ui.app_list;

import com.guavabot.marshpermissions.injection.ApplicationComponent;
import com.guavabot.marshpermissions.injection.ComponentScope;

import dagger.Component;

/**
 * Component with the lifetime of the {@link AppListActivity}
 */
@ComponentScope
@Component(dependencies = ApplicationComponent.class, modules = {AppListModule.class})
public interface AppListComponent {

    void inject(AppListActivity appListActivity);
}
