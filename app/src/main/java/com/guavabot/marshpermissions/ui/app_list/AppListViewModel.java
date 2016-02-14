package com.guavabot.marshpermissions.ui.app_list;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.guavabot.marshpermissions.BR;
import com.guavabot.marshpermissions.injection.ActivityScope;

import java.util.List;

import javax.inject.Inject;

@ActivityScope
class AppListViewModel extends BaseObservable {

    private List<AppViewModel> mApps;

    @Inject
    AppListViewModel() {
    }

    @Bindable
    public List<AppViewModel> getApps() {
        return mApps;
    }

    public void setApps(List<AppViewModel> apps) {
        if (!apps.equals(mApps)) {
            mApps = apps;
            notifyPropertyChanged(BR.apps);
        }
    }
}
