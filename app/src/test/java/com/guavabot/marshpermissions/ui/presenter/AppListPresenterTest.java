package com.guavabot.marshpermissions.ui.presenter;

import com.guavabot.marshpermissions.TestSchedulers;
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
import rx.android.plugins.RxAndroidPlugins;
import rx.functions.Action0;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        mTested = new AppListPresenter(mAppListView, mGetAppListUseCase, mSetAppHiddenUseCase,
                mAppSettings, new TestSchedulers());
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void shouldLoadAndSetItemsOnStart() throws Exception {
        List<App> apps = new ArrayList<>();
        apps.add(mock(App.class));
        given(mGetAppListUseCase.execute())
                .willReturn(Observable.just(apps));
        given(mAppSettings.isDisplayHidden())
                .willReturn(Observable.just(true));

        mTested.onStart();

        verify(mAppListView).setItems(apps);
        verify(mAppListView).setHideItemButtons(true);
    }

    @Test
    public void shouldStartAppInfoWhenItemClicked() throws Exception {
        App app = mock(App.class);
        given(app.getPackage()).willReturn(PACKAGE);

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
        given(mSetAppHiddenUseCase.execute(app))
                .willReturn(observable);

        mTested.onItemButtonClicked(app);

        verify(mSetAppHiddenUseCase).execute(any(App.class));
        assertThat(subscribed[0]).isTrue();
    }

}