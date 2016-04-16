package com.guavabot.marshpermissions.domain.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Set;

public class App {

    private final String mPackageName;
    private final String mName;
    private final boolean mHidden;
    private final Set<String> mPermissions;

    public App(String packageName, String name, boolean hidden, Set<String> permissions) {
        mPermissions = permissions;
        if (packageName == null) throw new NullPointerException();

        mPackageName = packageName;
        mName = name;
        mHidden = hidden;
    }

    /**
     * Application package.
     */
    @NonNull
    public String getPackage() {
        return mPackageName;
    }

    /**
     * Application package name.
     */
    @Nullable
    public String getName() {
        return mName;
    }

    /**
     * Has this app been manually hidden by the user?
     */
    public boolean isHidden() {
        return mHidden;
    }

    public Set<String> getPermissions() {
        return mPermissions;
    }

    /**
     * True if this app appears to be from Google.
     */
    public boolean isGoogleApp() {
        return getPackage().startsWith("com.google");
    }

    /**
     * True if this app appears to be from the Android system.
     */
    public boolean isAndroidApp() {
        return getPackage().equals("android") || getPackage().startsWith("com.android");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        App app = (App) o;

        if (mHidden != app.mHidden) return false;
        if (!mPackageName.equals(app.mPackageName)) return false;
        //noinspection SimplifiableIfStatement
        if (mName != null ? !mName.equals(app.mName) : app.mName != null) return false;
        return mPermissions.equals(app.mPermissions);

    }

    @Override
    public int hashCode() {
        int result = mPackageName.hashCode();
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mHidden ? 1 : 0);
        result = 31 * result + mPermissions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "App{" +
                "mPackageName='" + mPackageName + '\'' +
                ", mName='" + mName + '\'' +
                ", mHidden=" + mHidden +
                ", mPermissions=" + mPermissions +
                '}';
    }
}
