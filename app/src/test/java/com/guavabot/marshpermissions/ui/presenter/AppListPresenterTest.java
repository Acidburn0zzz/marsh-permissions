package com.guavabot.marshpermissions.ui.presenter;

import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;
import com.guavabot.marshpermissions.domain.interactor.GetAppListUseCase;
import com.guavabot.marshpermissions.domain.interactor.SetAppHiddenUseCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppListPresenterTest {

    private AppListPresenter mTested;

    @Mock
    private AppListView mAppListView;
    @Mock
    private GetAppListUseCase mGetAppListUseCase;
    @Mock
    private SetAppHiddenUseCase mSetAppHiddenUseCase;
    @Mock
    private AppSettings mAppSettings;
    private static final String PACKAGE = "com.my.package";

    @Before
    public void setUp() throws Exception {
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
        mTested = new AppListPresenter(mAppListView, mGetAppListUseCase, mSetAppHiddenUseCase, mAppSettings);
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void shouldLoadAndSetItemsOnStart() throws Exception {
        List<App> apps = new ArrayList<>();
        apps.add(mock(App.class));
        when(mGetAppListUseCase.execute())
                .thenReturn(Observable.just(apps));
        when(mAppSettings.isDisplayHidden())
                .thenReturn(Observable.just(true));

        mTested.onStart();

        verify(mAppListView).setItems(apps);
        verify(mAppListView).setHideItemButtons(true);
    }

    @Test
    public void shouldStartAppInfoWhenItemClicked() throws Exception {
        App app = mock(App.class);
        when(app.getPackage()).thenReturn(PACKAGE);

        mTested.onItemClicked(app);

        verify(mAppListView).startAppInfo(PACKAGE);
    }

    @Test
    public void shouldHideAppWhenItemButtonClicked() throws Exception {
        final boolean[] subscribed = {false};
        Observable<Void> observable = Observable
                .just((Void) null)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        subscribed[0] = true;
                    }
                });
        App app = mock(App.class);
        when(mSetAppHiddenUseCase.execute(app))
                .thenReturn(observable);

        mTested.onItemButtonClicked(app);

        verify(mSetAppHiddenUseCase).execute(any(App.class));
        assertThat(subscribed[0]).isTrue();
    }

}