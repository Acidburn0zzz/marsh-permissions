package com.guavabot.marshpermissions.domain.gateway;

import rx.Observable;

/**
 * <p>Created by Ivan on 12/29/15.
 */
public interface AppSettings {
    /**
     * Should apps hidden manually be displayed anyways?
     */
    Observable<Boolean> isDisplayHidden();

    /**
     * Should apps from Google be displayed?
     */
    Observable<Boolean> isDisplayGoogle();

    /**
     * Should Android system apps be displayed?
     */
    Observable<Boolean> isDisplayAndroid();
}
