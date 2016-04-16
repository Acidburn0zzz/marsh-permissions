package com.guavabot.marshpermissions.domain.interactor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.guavabot.marshpermissions.domain.entity.App;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Interactor for retrieving a filtered list of apps that can be shown according to the app settings.
 */
public class GetAppListFilteredUseCase implements UseCase {

    private final GetAppListUseCase mGetAppListUseCase;

    @Inject
    public GetAppListFilteredUseCase(GetAppListUseCase getAppListUseCase) {
        mGetAppListUseCase = getAppListUseCase;
    }

    /**
     * Returns a hot observable with lists of filtered apps to display.
     */
    public Observable<List<App>> execute(String filter) {
        return mGetAppListUseCase.execute()
                .map(apps -> Stream.of(apps)
                        .filter(app -> includeApp(filter, app))
                        .collect(Collectors.toList()))
                .distinctUntilChanged();
    }

    private boolean includeApp(String filter, App app) {
        return filter == null
                || filter.isEmpty()
                || app.getPackage().contains(filter);
    }
}
