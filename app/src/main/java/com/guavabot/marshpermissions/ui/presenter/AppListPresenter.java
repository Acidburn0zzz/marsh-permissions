package com.guavabot.marshpermissions.ui.presenter;

import com.guavabot.marshpermissions.Schedulers;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.interactor.GetAppListFilteredUseCase;
import com.guavabot.marshpermissions.domain.interactor.ToggleAppHiddenUseCase;
import com.guavabot.marshpermissions.injection.ActivityScope;
import com.guavabot.marshpermissions.ui.view.AppListViewModel;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Presenter for a list of apps in an {@link AppListView}.
 */
@ActivityScope
public class AppListPresenter implements Presenter {

    private final AppListView mAppListView;
    private final AppListViewModel mAppListViewModel;
    private final GetAppListFilteredUseCase mGetAppListFilteredUseCase;
    private final ToggleAppHiddenUseCase mToggleAppHiddenUseCase;
    private final Schedulers mSchedulers;

    private CompositeSubscription mSubscriptions;

    @Inject
    public AppListPresenter(AppListView appListView, AppListViewModel appListViewModel,
                            GetAppListFilteredUseCase getAppListFilteredUseCase,
                            ToggleAppHiddenUseCase toggleAppHiddenUseCase,
                            Schedulers schedulers) {
        mAppListView = appListView;
        mAppListViewModel = appListViewModel;
        mGetAppListFilteredUseCase = getAppListFilteredUseCase;
        mToggleAppHiddenUseCase = toggleAppHiddenUseCase;
        mSchedulers = schedulers;
    }

    @Override
    public void onStart() {
        mSubscriptions = new CompositeSubscription();
        Observable<String> packageFilterStream = mAppListView.getPackageFilter();
        mSubscriptions.add(mGetAppListFilteredUseCase.execute(packageFilterStream)
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.mainThread())
                .subscribe(new Action1<List<App>>() {
                    @Override
                    public void call(List<App> apps) {
                        mAppListViewModel.setApps(apps);
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
        mToggleAppHiddenUseCase.execute(app)
                .subscribeOn(mSchedulers.io())
                .subscribe();
    }

}
