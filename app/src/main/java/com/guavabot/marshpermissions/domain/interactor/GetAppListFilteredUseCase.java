package com.guavabot.marshpermissions.domain.interactor;

import com.guavabot.marshpermissions.domain.entity.App;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

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
        Observable<Observable<List<App>>> stream = packageFilter
                .map(new Func1<String, Observable<List<App>>>() {
                    @Override
                    public Observable<List<App>> call(final String filter) {
                        return mGetAppListUseCase.execute()
                                .flatMap(new Func1<List<App>, Observable<List<App>>>() {
                                    @Override
                                    public Observable<List<App>> call(List<App> apps) {
                                        return Observable.from(apps)
                                                .filter(new Func1<App, Boolean>() {
                                                    @Override
                                                    public Boolean call(App app) {
                                                        return filter == null || filter.isEmpty() || app.getPackage().contains(filter);
                                                    }
                                                })
                                                .toList();
                                    }
                                });
                    }
                });
        return Observable.switchOnNext(stream)
                .distinctUntilChanged();
    }

}
