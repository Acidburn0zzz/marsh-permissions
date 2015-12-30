package com.guavabot.marshpermissions.data;

import com.guavabot.marshpermissions.domain.entity.App;

public class AppImpl extends App {

    private final String mPackage;
    private final boolean mHidden;

    public AppImpl(String aPackage, boolean hidden) {
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
}
