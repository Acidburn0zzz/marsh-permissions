package com.guavabot.marshpermissions.ui;

/**
 * A presenter that will be started or stopped with the lifecycle of an Activity or Fragment.
 */
public interface Presenter {

    void onStart();

    void onStop();
}
