package com.guavabot.marshpermissions.domain.interactor;

import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;

import rx.Observable;

/**
 * Interactor for marking an app as hidden.
 */
public class ToggleAppHiddenUseCase implements UseCase {

    private final AppRepository mAppRepository;

    public ToggleAppHiddenUseCase(AppRepository appRepository) {
        mAppRepository = appRepository;
    }

    public Observable<Void> execute(App app) {
        if (app.isHidden()) {
            return mAppRepository.setAppNotHidden(app.getPackage());
        } else {
            return mAppRepository.setAppHidden(app.getPackage());
        }
    }

}
