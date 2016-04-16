package com.guavabot.marshpermissions.ui.app_list;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.guavabot.marshpermissions.BR;

import java.util.Set;

class AppViewModel extends BaseObservable {

    private final String mPackage;
    private final String mName;
    private final Set<String> mPermissions;

    private boolean mHidden;

    AppViewModel(String aPackage, String name, boolean hidden, Set<String> permissions) {
        mPermissions = permissions;
        if (aPackage == null) throw new NullPointerException();
        mPackage = aPackage;
        mName = name;
        mHidden = hidden;
    }

    @NonNull
    public String getPackage() {
        return mPackage;
    }

    @Nullable
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

    public Set<String> getPermissions() {
        return mPermissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppViewModel that = (AppViewModel) o;

        if (mHidden != that.mHidden) return false;
        if (!mPackage.equals(that.mPackage)) return false;
        //noinspection SimplifiableIfStatement
        if (mName != null ? !mName.equals(that.mName) : that.mName != null) return false;
        return mPermissions.equals(that.mPermissions);

    }

    @Override
    public int hashCode() {
        int result = mPackage.hashCode();
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mHidden ? 1 : 0);
        result = 31 * result + mPermissions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AppViewModel{" +
                "mPackage='" + mPackage + '\'' +
                ", mName='" + mName + '\'' +
                ", mHidden=" + mHidden +
                ", mPermissions=" + mPermissions +
                '}';
    }
}
