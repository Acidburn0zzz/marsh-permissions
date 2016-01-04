package com.guavabot.marshpermissions.ui.presenter;

import com.guavabot.marshpermissions.Schedulers;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;
import com.guavabot.marshpermissions.domain.interactor.GetAppListUseCase;
import com.guavabot.marshpermissions.domain.interactor.SetAppHiddenUseCase;
import com.guavabot.marshpermissions.injection.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter for a list of apps in an {@link AppListView}.
 */
@ActivityScope
public class AppListPresenter implements Presenter {

    private final AppListView mAppListView;
    private final GetAppListUseCase mGetAppListUseCase;
    private final SetAppHiddenUseCase mSetAppHiddenUseCase;
    private final AppSettings mAppSettings;
    private final Schedulers mSchedulers;

    private CompositeSubscription mSubscriptions;

    @Inject
    public AppListPresenter(AppListView appListView, GetAppListUseCase getAppListUseCase,
                            SetAppHiddenUseCase setAppHiddenUseCase, AppSettings appSettings,
                            Schedulers schedulers) {
        mAppListView = appListView;
        mGetAppListUseCase = getAppListUseCase;
        mSetAppHiddenUseCase = setAppHiddenUseCase;
        mAppSettings = appSettings;
        mSchedulers = schedulers;
    }

    @Override
    public void onStart() {
        mSubscriptions = new CompositeSubscription();
        mSubscriptions.add(mGetAppListUseCase.execute()
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.mainThread())
                .subscribe(new Action1<List<App>>() {
                    @Override
                    public void call(List<App> apps) {
                        mAppListView.setItems(apps);
                    }
                }));
        mSubscriptions.add(mAppSettings.isDisplayHidden()
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean displayHidden) {
                        mAppListView.setHideItemButtons(displayHidden);
                    }
                }));
    }

    @Override
    public void onStop() {
        mSubscriptions.unsubscribe();
    }

    /**
     * An app item was clicked.
     */
    public void onItemClicked(App app) {
        mAppListView.startAppInfo(app.getPackage());
    }

    /**
     * The button for an item was clicked.
     */
    public void onItemButtonClicked(App app) {
        mSetAppHiddenUseCase.execute(app)
                .subscribeOn(mSchedulers.io())
                .subscribe();
    }

}
