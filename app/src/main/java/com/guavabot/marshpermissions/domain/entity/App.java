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
}
