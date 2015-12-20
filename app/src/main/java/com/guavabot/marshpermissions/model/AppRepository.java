package com.guavabot.marshpermissions.model;

import java.util.List;

/**
 * Repository for retrieving a list of {@link App}.
 */
public interface AppRepository {

    /**
     * Find the list of apps targeting Marshmallow.
     * @param hidden True to include apps marked hidden
     * @param google True to include Google apps
     * @param android True to include apps from the Android system
     */
    List<App> findAppsMarshmallow(boolean hidden, boolean google, boolean android);

    /**
     * Mark an app as hidden.
     */
    void setAppHidden(App app);
}
