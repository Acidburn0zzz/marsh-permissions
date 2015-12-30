package com.guavabot.marshpermissions.domain.gateway;

import com.guavabot.marshpermissions.domain.entity.App;

import java.util.List;

import rx.Observable;

/**
 * Repository for retrieving a list of {@link App}.
 */
public interface AppRepository {

    /**
     * Find the list of apps installed targeting Marshmallow.
     */
    Observable<List<App>> findAppsMarshmallow();

    /**
     * Mark an app as hidden.
     */
    Observable<Void> setAppHidden(App app);

    /**
     * Pushes a notification each time an app is hidden by the user.
     */
    Observable<Void> hiddenAppsUpdate();
}
