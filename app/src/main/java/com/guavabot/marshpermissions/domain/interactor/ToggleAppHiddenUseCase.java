package com.guavabot.marshpermissions.domain.interactor;

import com.guavabot.marshpermissions.domain.gateway.AppRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Interactor for marking an app as hidden.
 */
public class ToggleAppHiddenUseCase implements UseCase {

    private final AppRepository mAppRepository;

    @Inject
    public ToggleAppHiddenUseCase(AppRepository appRepository) {
        mAppRepository = appRepository;
    }

    public Observable<Void> execute(String packageName, boolean hidden) {
        if (hidden) {
            return mAppRepository.setAppHidden(packageName);
        } else {
            return mAppRepository.setAppNotHidden(packageName);
        }
    }

}
