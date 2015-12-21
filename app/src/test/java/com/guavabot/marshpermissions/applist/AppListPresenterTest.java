package com.guavabot.marshpermissions.applist;

import com.guavabot.marshpermissions.model.App;
import com.guavabot.marshpermissions.model.AppRepository;
import com.guavabot.marshpermissions.settings.AppSettings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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
    private AppRepository mAppRepository;
    @Mock
    private AppSettings mAppSettings;
    private static final String PACKAGE = "com.my.package";

    @Before
    public void setUp() throws Exception {
        mTested = new AppListPresenter(mAppListView, mAppRepository, mAppSettings);
    }

    @Test
    public void shouldSetItemsWhenLoading() throws Exception {
        List<App> apps = new ArrayList<>();
        apps.add(new App(PACKAGE));
        apps.add(new App(PACKAGE));
        when(mAppRepository.findAppsMarshmallow())
                .thenReturn(apps);

        mTested.load();

        verify(mAppListView).setItems(apps);
    }

    @Test
    public void shouldStartAppInfoWhenItemClicked() throws Exception {
        App app = mock(App.class);
        when(app.getPackage()).thenReturn(PACKAGE);

        mTested.onItemClicked(app);

        verify(mAppListView).startAppInfo(PACKAGE);
    }

    @Test
    public void shouldHideAppAndReloadWhenItemButtonClicked() throws Exception {
        App app = new App(PACKAGE);

        mTested.onItemButtonClicked(app);

        verify(mAppRepository).setAppHidden(any(App.class));
        verify(mAppListView).setItems(Matchers.<List<App>>any());
    }

    @Test
    public void shouldHideItemButtonWhenHiddenAppsAreDisplayed() throws Exception {
        when(mAppSettings.isDisplayHidden()).thenReturn(true);
        assertThat(mTested.isHideItemButton(), is(true));
    }
}