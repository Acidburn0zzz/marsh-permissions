package com.guavabot.marshpermissions.applist;

import com.guavabot.marshpermissions.injection.ActivityModule;
import com.guavabot.marshpermissions.injection.ApplicationComponent;
import com.guavabot.marshpermissions.injection.PerActivity;

import dagger.Component;

/**
 * Component with the lifetime of the {@link AppListActivity}
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, AppListModule.class})
public interface AppListComponent {

    void inject(AppListActivity appListActivity);
}
