package com.guavabot.marshpermissions.domain.entity;

public abstract class App {

    /**
     * Application package name.
     */
    public abstract String getPackage();

    /**
     * Has this app been manually hidden by the user?
     */
    public abstract boolean isHidden();

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
}
