package com.guavabot.marshpermissions.ui.presenter;

import com.guavabot.marshpermissions.TestSchedulers;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.interactor.GetAppListFilteredUseCase;
import com.guavabot.marshpermissions.domain.interactor.ToggleAppHiddenUseCase;

import org.junit.After;
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
import rx.android.plugins.RxAndroidPlugins;
import rx.functions.Action0;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AppListPresenterTest {

    private AppListPresenter mTested;

    @Mock
    private AppListView mAppListView;
    @Mock
    private GetAppListFilteredUseCase mGetAppListFilteredUseCase;
    @Mock
    private ToggleAppHiddenUseCase mToggleAppHiddenUseCase;
    private static final String PACKAGE = "com.my.package";

    @Before
    public void setUp() throws Exception {
        mTested = new AppListPresenter(mAppListView, mGetAppListFilteredUseCase, mToggleAppHiddenUseCase,
                new TestSchedulers());

        given(mAppListView.getPackageFilter()).willReturn(Observable.just(""));
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void shouldLoadAndSetItemsOnStart() throws Exception {
        final List<App> apps = new ArrayList<>();
        apps.add(mock(App.class));
        given(mGetAppListFilteredUseCase.execute(Matchers.<Observable<String>>any()))
                .willReturn(Observable.create(new Observable.OnSubscribe<List<App>>() {
                    @Override
                    public void call(Subscriber<? super List<App>> subscriber) {
                        subscriber.onNext(apps);
                    }
                }));

        mTested.onStart();

        verify(mAppListView).getPackageFilter();
        verify(mAppListView).setItems(apps);
    }

    @Test
    public void shouldStartAppInfoWhenItemClicked() throws Exception {
        App app = mock(App.class);
        given(app.getPackage()).willReturn(PACKAGE);

        mTested.onItemClicked(app);

        verify(mAppListView).startAppInfo(PACKAGE);
    }

    @Test
    public void shouldToggleAppHiddenWhenItemButtonClicked() throws Exception {
        final boolean[] subscribed = {false};
        App app = mock(App.class);
        given(mToggleAppHiddenUseCase.execute(app))
                .willReturn(Observable.just((Void) null)
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                subscribed[0] = true;
                            }
                        }));

        mTested.onItemButtonClicked(app);

        verify(mToggleAppHiddenUseCase).execute(app);
        assertThat(subscribed[0]).isTrue();
    }

}