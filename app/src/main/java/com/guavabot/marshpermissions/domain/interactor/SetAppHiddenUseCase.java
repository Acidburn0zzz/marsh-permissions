package com.guavabot.marshpermissions.domain.interactor;

import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;

import rx.Observable;

/**
 * Interactor for marking an app as hidden.
 */
public class SetAppHiddenUseCase implements UseCase {

    private final AppRepository mAppRepository;

    public SetAppHiddenUseCase(AppRepository appRepository) {
        mAppRepository = appRepository;
    }

    public Observable<Void> execute(App app) {
        return mAppRepository.setAppHidden(app);
    }

}
