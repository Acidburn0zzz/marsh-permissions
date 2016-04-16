package com.guavabot.marshpermissions;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Instance factory methods for retrieving Schedulers.
 */
@Singleton
public class Schedulers {

    @Inject
    public Schedulers() { }

    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler io() {
        return rx.schedulers.Schedulers.io();
    }
}
