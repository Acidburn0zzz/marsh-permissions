package com.guavabot.marshpermissions.ui.app_list;

import com.guavabot.marshpermissions.Schedulers;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.interactor.GetAppListFilteredUseCase;
import com.guavabot.marshpermissions.domain.interactor.ToggleAppHiddenUseCase;
import com.guavabot.marshpermissions.injection.ActivityScope;
import com.guavabot.marshpermissions.ui.Presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
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

    private final CompositeSubscription mSubscriptions = new CompositeSubscription();

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
        Observable<String> packageFilterStream = mAppListView.getPackageFilter();
        mSubscriptions.add(mGetAppListFilteredUseCase.execute(packageFilterStream)
                .map((apps) -> Mapper.map(apps))
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.mainThread())
                .subscribe(viewModels -> {
                    mAppListViewModel.setApps(viewModels);
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
        mAppListView.startAppInfo(app.getName());
    }

    /**
     * The button for an item was clicked.
     */
    public void onItemButtonClicked(AppViewModel app) {
        app.toggleHidden();
        mToggleAppHiddenUseCase.execute(app.getName(), app.isHidden())
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
            return new AppViewModel(app.getPackage(), app.isHidden());
        }
    }
}
