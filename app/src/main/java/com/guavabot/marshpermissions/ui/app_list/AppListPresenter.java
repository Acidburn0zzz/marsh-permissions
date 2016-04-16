package com.guavabot.marshpermissions.ui.app_list;

import com.guavabot.marshpermissions.Schedulers;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.interactor.GetAppListFilteredUseCase;
import com.guavabot.marshpermissions.domain.interactor.ToggleAppHiddenUseCase;
import com.guavabot.marshpermissions.injection.ComponentScope;
import com.guavabot.marshpermissions.ui.Presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Presenter for a list of apps in an {@link AppListView}.
 */
@ComponentScope
public class AppListPresenter implements Presenter {

    private final AppListView mAppListView;
    private final GetAppListFilteredUseCase mGetAppListFilteredUseCase;
    private final ToggleAppHiddenUseCase mToggleAppHiddenUseCase;
    private final Schedulers mSchedulers;

    private final CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Inject
    public AppListPresenter(AppListView appListView,
                            GetAppListFilteredUseCase getAppListFilteredUseCase,
                            ToggleAppHiddenUseCase toggleAppHiddenUseCase,
                            Schedulers schedulers) {
        mAppListView = appListView;
        mGetAppListFilteredUseCase = getAppListFilteredUseCase;
        mToggleAppHiddenUseCase = toggleAppHiddenUseCase;
        mSchedulers = schedulers;
    }

    @Override
    public void onStart() {
        mSubscriptions.add(mAppListView.getSearchQuery()
                .doOnNext(__ -> mAppListView.setLoading(true))
                .subscribeOn(mSchedulers.mainThread())
                .onBackpressureLatest()
                .observeOn(mSchedulers.io())
                .flatMap(filter -> mGetAppListFilteredUseCase.execute(filter))
                .map((apps) -> Mapper.map(apps))
                .observeOn(mSchedulers.mainThread())
                .doOnNext(__ -> mAppListView.setLoading(false))
                .distinctUntilChanged()
                .subscribe(viewModels -> {
                    mAppListView.setApps(viewModels);
                }));
    }

    @Override
    public void onStop() {
        mSubscriptions.clear();
    }

    /**
     * An app item was clicked.
     */
    public void onItemClicked(AppViewModel app) {
        mAppListView.startAppInfo(app.getPackage());
    }

    /**
     * The button for an item was clicked.
     */
    public void onItemButtonClicked(AppViewModel app) {
        app.toggleHidden();
        mToggleAppHiddenUseCase.execute(app.getPackage(), app.isHidden())
                .subscribeOn(mSchedulers.io())
                .subscribe();
    }

    static class Mapper {

        static List<AppViewModel> map(List<App> apps) {
            List<AppViewModel> viewModelApps = new ArrayList<>(apps.size());
            for (App app : apps) {
                viewModelApps.add(map(app));
            }
            return viewModelApps;
        }

        static AppViewModel map(App app) {
            return new AppViewModel(app.getPackage(), app.getName(), app.isHidden(), app.getPermissions());
        }
    }
}
