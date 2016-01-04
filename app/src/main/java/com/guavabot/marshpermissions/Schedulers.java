package com.guavabot.marshpermissions;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Instance factory methods for retrieving Schedulers.
 */
public class Schedulers {

    public Schedulers() { }

    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler io() {
        return rx.schedulers.Schedulers.io();
    }
}
