package com.guavabot.marshpermissions;

import rx.Observable;
import rx.Single;

public class SubscriptionTester<T> {

    private boolean mSubscribed;
    private boolean mUnsubscribed;

    public Observable<T> get() {
        return Observable.<T>never()
                .doOnSubscribe(() -> mSubscribed = true)
                .doOnUnsubscribe(() -> mUnsubscribed = true);
    }

    public Single<T> getSingle() {
        return get().toSingle();
    }

    public boolean wasSubscribed() {
        return mSubscribed;
    }

    public boolean wasUnsubscribed() {
        return mUnsubscribed;
    }
}
