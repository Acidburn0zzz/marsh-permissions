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
import rx.functions.Action1;
import rx.functions.Func1;
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
    private final Mapper mMapper = new Mapper();

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
                .map(mMapper)
                .subscribeOn(mSchedulers.io())
                .observeOn(mSchedulers.mainThread())
                .subscribe(new Action1<List<AppViewModel>>() {
                    @Override
                    public void call(List<AppViewModel> apps) {
                        mAppListViewModel.setApps(apps);
                    }
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

    static class Mapper implements Func1<List<App>, List<AppViewModel>> {

        @Override
        public List<AppViewModel> call(List<App> apps) {
            List<AppViewModel> viewModelApps = new ArrayList<>(apps.size());
            for (App app : apps) {
                viewModelApps.add(map(app));
            }
            return viewModelApps;
        }

        private AppViewModel map(App app) {
            return new AppViewModel(app.getPackage(), app.isHidden());
        }
    }
}
