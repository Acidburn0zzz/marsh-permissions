package com.guavabot.marshpermissions.domain.entity;

public class App {

    private final String mPackage;
    private final boolean mHidden;

    public App(String aPackage, boolean hidden) {
        mPackage = aPackage;
        mHidden = hidden;
    }

    /**
     * Application package name.
     */
    public String getPackage() {
        return mPackage;
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

        //noinspection SimplifiableIfStatement
        if (mHidden != app.mHidden) return false;
        return mPackage.equals(app.mPackage);

    }

    @Override
    public int hashCode() {
        int result = mPackage.hashCode();
        result = 31 * result + (mHidden ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "App{" +
                "mPackage='" + mPackage + '\'' +
                ", mHidden=" + mHidden +
                '}';
    }
}
