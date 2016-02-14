package com.guavabot.marshpermissions.ui.app_list;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.guavabot.marshpermissions.BR;

class AppViewModel extends BaseObservable {

    private final String mName;

    private boolean mHidden;

    AppViewModel(String name, boolean hidden) {
        mName = name;
        mHidden = hidden;
    }

    public String getName() {
        return mName;
    }

    @Bindable
    public boolean isHidden() {
        return mHidden;
    }

    void toggleHidden() {
        mHidden = !mHidden;
        notifyPropertyChanged(BR.hidden);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppViewModel that = (AppViewModel) o;

        //noinspection SimplifiableIfStatement
        if (mHidden != that.mHidden) return false;
        return mName.equals(that.mName);

    }

    @Override
    public int hashCode() {
        int result = mName.hashCode();
        result = 31 * result + (mHidden ? 1 : 0);
        return result;
    }
}
