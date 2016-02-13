package com.guavabot.marshpermissions.domain.interactor;

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

    public Observable<Void> execute(String appPackage, boolean hidden) {
        if (hidden) {
            return mAppRepository.setAppHidden(appPackage);
        } else {
            return mAppRepository.setAppNotHidden(appPackage);
        }
    }

}
