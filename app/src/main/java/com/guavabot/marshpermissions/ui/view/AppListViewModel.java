package com.guavabot.marshpermissions.ui.view;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.guavabot.marshpermissions.BR;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.injection.ActivityScope;

import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class AppListViewModel extends BaseObservable {

    private List<App> mApps;

    @Inject
    public AppListViewModel() {
    }

    @Bindable
    public List<App> getApps() {
        return mApps;
    }

    public void setApps(List<App> apps) {
        if (!apps.equals(mApps)) {
            mApps = apps;
            notifyPropertyChanged(BR.apps);
        }
    }
}
