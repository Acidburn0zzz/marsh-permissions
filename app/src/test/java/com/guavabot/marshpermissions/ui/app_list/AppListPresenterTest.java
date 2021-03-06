package com.guavabot.marshpermissions.ui.app_list;

import com.guavabot.marshpermissions.SubscriptionTester;
import com.guavabot.marshpermissions.TestSchedulers;
import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.interactor.GetAppListFilteredUseCase;
import com.guavabot.marshpermissions.domain.interactor.ToggleAppHiddenUseCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AppListPresenterTest {

    private AppListPresenter mTested;

    @Mock AppListView mAppListView;
    @Mock GetAppListFilteredUseCase mGetAppListFilteredUseCase;
    @Mock ToggleAppHiddenUseCase mToggleAppHiddenUseCase;

    private static final String PACKAGE = "com.my.package";

    @Before
    public void setUp() throws Exception {
        mTested = new AppListPresenter(mAppListView, mGetAppListFilteredUseCase, mToggleAppHiddenUseCase,
                new TestSchedulers());
    }

    @Test
    public void shouldLoadAndSetItemsOnStart() throws Exception {
        String query = "My app";
        given(mAppListView.getSearchQuery()).willReturn(Observable.just(query));
        SubscriptionTester<List<App>> subscriptionTester = new SubscriptionTester<>();
        given(mGetAppListFilteredUseCase.execute(query)).willReturn(subscriptionTester.get());

        mTested.onStart();

        assertThat(subscriptionTester.wasSubscribed()).isTrue();
    }

    @Test
    public void shouldStartAppInfoWhenItemClicked() throws Exception {
        AppViewModel app = getFakeViewModels().get(0);

        mTested.onItemClicked(app);

        verify(mAppListView).startAppInfo(app.getPackage());
    }

    @Test
    public void shouldToggleAppHiddenWhenItemButtonClicked() throws Exception {
        AppViewModel app = getFakeViewModels().get(0);
        final boolean hidden = app.isHidden();
        final boolean[] subscribed = {false};
        given(mToggleAppHiddenUseCase.execute(app.getPackage(), true))
                .willReturn(Observable.just((Void) null)
                        .doOnSubscribe(() -> subscribed[0] = true));

        mTested.onItemButtonClicked(app);

        assertThat(app.isHidden()).isNotEqualTo(hidden);
        assertThat(subscribed[0]).isTrue();
    }

    @Test
    public void shouldMapCorrectly() {
        List<App> apps = getFakeApps();

        List<AppViewModel> result = AppListPresenter.Mapper.map(apps);

        assertThat(result).isEqualTo(getFakeViewModels());
    }

    private List<App> getFakeApps() {
        List<App> apps = new ArrayList<>();
        apps.add(new App(PACKAGE, "one", false, Collections.singleton("permission")));
        apps.add(new App(PACKAGE + "2", "two", true, Collections.emptySet()));
        apps.add(new App(PACKAGE + "3", null, false, Collections.emptySet()));
        return apps;
    }

    private List<AppViewModel> getFakeViewModels() {
        List<AppViewModel> apps = new ArrayList<>();
        apps.add(new AppViewModel(PACKAGE, "one", false, Collections.singleton("permission")));
        apps.add(new AppViewModel(PACKAGE + "2", "two", true, Collections.emptySet()));
        apps.add(new AppViewModel(PACKAGE + "3", null, false, Collections.emptySet()));
        return apps;
    }
}
