package com.guavabot.marshpermissions.domain.interactor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.guavabot.marshpermissions.domain.entity.App;

import java.util.List;

import rx.Observable;

/**
 * Interactor for retrieving a filtered list of apps that can be shown according to the app settings.
 */
public class GetAppListFilteredUseCase implements UseCase {

    private final GetAppListUseCase mGetAppListUseCase;

    public GetAppListFilteredUseCase(GetAppListUseCase getAppListUseCase) {
        mGetAppListUseCase = getAppListUseCase;
    }

    /**
     * Returns a hot observable with lists of filtered apps to display.
     */
    public Observable<List<App>> execute(Observable<String> packageFilter) {
        return packageFilter
                .flatMap(filter -> mGetAppListUseCase.execute()
                                .map(apps -> Stream.of(apps)
                                        .filter(app -> filter == null
                                                || filter.isEmpty()
                                                || app.getPackage().contains(filter))
                                        .collect(Collectors.toList()))
                )
                .distinctUntilChanged();
    }
}
