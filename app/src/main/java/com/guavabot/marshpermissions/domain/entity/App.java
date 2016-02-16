package com.guavabot.marshpermissions.domain.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class App {

    private final String mPackage;
    private final String mName;
    private final boolean mHidden;

    public App(String aPackage, String name, boolean hidden) {
        if (aPackage == null) throw new NullPointerException();

        mPackage = aPackage;
        mName = name;
        mHidden = hidden;
    }

    /**
     * Application package.
     */
    @NonNull
    public String getPackage() {
        return mPackage;
    }

    /**
     * Application name.
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
        return getPackage().startsWith("com.android");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        App app = (App) o;

        if (mHidden != app.mHidden) return false;
        //noinspection SimplifiableIfStatement
        if (!mPackage.equals(app.mPackage)) return false;
        return !(mName != null ? !mName.equals(app.mName) : app.mName != null);

    }

    @Override
    public int hashCode() {
        int result = mPackage.hashCode();
        result = 31 * result + (mName != null ? mName.hashCode() : 0);
        result = 31 * result + (mHidden ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "App{" +
                "mPackage='" + mPackage + '\'' +
                ", mName='" + mName + '\'' +
                ", mHidden=" + mHidden +
                '}';
    }
}
