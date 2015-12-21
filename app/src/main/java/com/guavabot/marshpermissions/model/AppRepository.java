package com.guavabot.marshpermissions.model;

import java.util.List;

/**
 * Repository for retrieving a list of {@link App}.
 */
public interface AppRepository {

    /**
     * Find the list of apps targeting Marshmallow.
     */
    List<App> findAppsMarshmallow();

    /**
     * Mark an app as hidden.
     */
    void setAppHidden(App app);
}
