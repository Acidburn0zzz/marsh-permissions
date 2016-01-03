package com.guavabot.marshpermissions.data;

import com.guavabot.marshpermissions.domain.entity.App;

class AppImpl extends App {

    private final String mPackage;
    private final boolean mHidden;

    AppImpl(String aPackage, boolean hidden) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppImpl app = (AppImpl) o;

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
        return "AppImpl{" +
                "mPackage='" + mPackage + '\'' +
                ", mHidden=" + mHidden +
                '}';
    }
}
