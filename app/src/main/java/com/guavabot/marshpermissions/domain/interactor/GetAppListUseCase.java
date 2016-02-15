package com.guavabot.marshpermissions.domain.interactor;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;

import java.util.List;

import rx.Observable;

/**
 * Interactor for retrieving the list of apps that can be shown according to the app settings.
 */
public class GetAppListUseCase implements UseCase {

    private final AppRepository mAppRepository;
    private final AppSettings mAppSettings;

    public GetAppListUseCase(AppRepository appRepository, AppSettings appSettings) {
        mAppRepository = appRepository;
        mAppSettings = appSettings;
    }

    /**
     * Returns a hot observable with lists of apps to display.
     */
    public Observable<List<App>> execute() {
        //The stream will emit update events
        return mAppRepository.hiddenAppsUpdate()
                .startWith((Void) null)
                .flatMap(s -> appsToDisplay());
    }

    private Observable<List<App>> appsToDisplay() {
        return Observable.combineLatest(
                mAppRepository.findAppsMarshmallow(),
                mAppSettings.isDisplayHidden(),
                mAppSettings.isDisplayGoogle(),
                mAppSettings.isDisplayAndroid(),
                (apps, hidden, google, android) ->
                        Stream.of(apps)
                                .filter(app -> (hidden || !app.isHidden())
                                        && (google || !app.isGoogleApp())
                                        && (android || !app.isAndroidApp()))
                                .collect(Collectors.toList())
        );
    }
}
