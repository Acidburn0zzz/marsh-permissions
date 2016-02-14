package com.guavabot.marshpermissions.ui.app_list;

import com.guavabot.marshpermissions.TestSchedulers;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.interactor.GetAppListFilteredUseCase;
import com.guavabot.marshpermissions.domain.interactor.ToggleAppHiddenUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AppListPresenterTest {

    private AppListPresenter mTested;

    @Mock AppListView mAppListView;
    @Mock GetAppListFilteredUseCase mGetAppListFilteredUseCase;
    @Mock ToggleAppHiddenUseCase mToggleAppHiddenUseCase;
    @Mock AppListViewModel mAppListViewModel;

    private static final String PACKAGE = "com.my.package";

    @Before
    public void setUp() throws Exception {
        mTested = new AppListPresenter(mAppListView, mAppListViewModel, mGetAppListFilteredUseCase, mToggleAppHiddenUseCase,
                new TestSchedulers());

        given(mAppListView.getPackageFilter()).willReturn(Observable.just(""));
    }

    @Test
    public void shouldLoadAndSetItemsOnStart() throws Exception {
        final List<App> fakeApps = getFakeApps();
        given(mGetAppListFilteredUseCase.execute(Matchers.<Observable<String>>any()))
                .willReturn(Observable.create(new Observable.OnSubscribe<List<App>>() {
                    @Override
                    public void call(Subscriber<? super List<App>> subscriber) {
                        subscriber.onNext(fakeApps);
                    }
                }));

        mTested.onStart();

        verify(mAppListView).getPackageFilter();
        verify(mAppListViewModel).setApps(new AppListPresenter.Mapper().call(fakeApps));
    }

    @Test
    public void shouldStartAppInfoWhenItemClicked() throws Exception {
        AppViewModel app = getFakeViewModels().get(0);

        mTested.onItemClicked(app);

        verify(mAppListView).startAppInfo(app.getName());
    }

    @Test
    public void shouldToggleAppHiddenWhenItemButtonClicked() throws Exception {
        AppViewModel app = getFakeViewModels().get(0);
        final boolean[] subscribed = {false};
        given(mToggleAppHiddenUseCase.execute(app.getName(), true))
                .willReturn(Observable.just((Void) null)
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                subscribed[0] = true;
                            }
                        }));

        mTested.onItemButtonClicked(app);

        verify(mToggleAppHiddenUseCase).execute(app.getName(), true);
        assertThat(subscribed[0]).isTrue();
    }

    @Test
    public void shouldMapCorrectly() {
        List<App> apps = getFakeApps();
        AppListPresenter.Mapper mapper = new AppListPresenter.Mapper();

        List<AppViewModel> result = mapper.call(apps);

        assertThat(result).isEqualTo(getFakeViewModels());
    }

    private List<App> getFakeApps() {
        List<App> apps = new ArrayList<>();
        apps.add(new App(PACKAGE, false));
        apps.add(new App(PACKAGE + "2", true));
        apps.add(new App(PACKAGE + "3", false));
        return apps;
    }

    private List<AppViewModel> getFakeViewModels() {
        List<AppViewModel> apps = new ArrayList<>();
        apps.add(new AppViewModel(PACKAGE, false));
        apps.add(new AppViewModel(PACKAGE + "2", true));
        apps.add(new AppViewModel(PACKAGE + "3", false));
        return apps;
    }
}