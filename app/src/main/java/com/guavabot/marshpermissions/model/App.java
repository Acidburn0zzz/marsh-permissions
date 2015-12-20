package com.guavabot.marshpermissions.model;

public class App {

    private final String mPackage;

    public App(String aPackage) {
        mPackage = aPackage;
    }

    public String getPackage() {
        return mPackage;
    }
}
