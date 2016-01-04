package com.guavabot.marshpermissions;

import rx.Scheduler;

/**
 * Instance factory methods that always retrieve an immediate Scheduler.
 */
public class TestSchedulers extends Schedulers {

    private final Scheduler mTestScheduler = rx.schedulers.Schedulers.immediate();

    public TestSchedulers() { }

    public Scheduler mainThread() {
        return mTestScheduler;
    }

    public Scheduler io() {
        return mTestScheduler;
    }
}