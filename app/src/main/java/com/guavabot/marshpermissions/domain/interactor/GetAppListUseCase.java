package com.guavabot.marshpermissions.domain.interactor;

import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func4;

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

    public Observable<List<App>> execute() {
        //The stream will emit new
        return mAppRepository.hiddenAppsUpdate()
                .startWith((Void) null)
                .flatMap(new Func1<Void, Observable<List<App>>>() {
                    @Override
                    public Observable<List<App>> call(Void s) {
                        return appsToDisplay();
                    }
                });
    }

    private Observable<List<App>> appsToDisplay() {
        return Observable.combineLatest(
                mAppRepository.findAppsMarshmallow(),
                mAppSettings.isDisplayHidden(),
                mAppSettings.isDisplayGoogle(),
                mAppSettings.isDisplayAndroid(),
                new Func4<List<App>, Boolean, Boolean, Boolean, List<App>>() {
                    @Override
                    public List<App> call(List<App> appsIn, Boolean hidden, Boolean google, Boolean android) {
                        List<App> apps = new ArrayList<>();
                        for (App app : appsIn) {
                            if ((hidden || !app.isHidden())
                                    && (google || !app.isGoogleApp())
                                    && (android || !app.isAndroidApp())) {
                                apps.add(app);
                            }
                        }
                        return apps;
                    }
                }
        );
    }
}
