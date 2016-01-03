package com.guavabot.marshpermissions.data;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.guavabot.marshpermissions.domain.entity.App;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Mockito.verify;

/**
 * <p>Created by Ivan on 1/3/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class SharedPrefsAppRepositoryTest {

    @Mock
    PackageManager mPackageManager;
    @Mock
    HiddenPackages mHiddenPackages;

    private SharedPrefsAppRepository mTested;

    @Before
    public void setUp() throws Exception {
        mTested = new SharedPrefsAppRepository(mPackageManager, mHiddenPackages);

        ArrayList<ApplicationInfo> list = new ArrayList<>();
        ApplicationInfo app1 = new ApplicationInfo();
        app1.targetSdkVersion = 22;
        app1.packageName = "package1";
        list.add(app1);
        ApplicationInfo app2 = new ApplicationInfo();
        app2.targetSdkVersion = 23;
        app2.packageName = "package2";
        list.add(app2);
        ApplicationInfo app3 = new ApplicationInfo();
        app3.targetSdkVersion = 24;
        app3.packageName = "package3";
        list.add(app3);
        given(mPackageManager.getInstalledApplications(anyInt()))
                .willReturn(list);
        HashSet<String> hiddenAppsSet = new HashSet<>();
        hiddenAppsSet.add("package2");
        given(mHiddenPackages.get())
                .willReturn(hiddenAppsSet);
    }

    @Test
    public void shouldOnlyReturnAppsThatTargetMarshmallow() {
        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();
        mTested.findAppsMarshmallow()
                .subscribe(subscriber);

        subscriber.assertValueCount(1);
        subscriber.assertCompleted();
        List<App> result = subscriber.getOnNextEvents().get(0);
        assertThat(result).containsOnly(new AppImpl("package2", true), new AppImpl("package3", false));
    }

    @Test
    public void shouldReturnHotUpdatesObservable() {
        TestSubscriber<Void> subscriber = new TestSubscriber<>();
        mTested.hiddenAppsUpdate()
                .subscribe(subscriber);

        subscriber.assertNoValues();
        subscriber.assertNoTerminalEvent();
    }

    @Test
    public void shouldPushUpdateWhenSettingAppHidden() {
        TestSubscriber<Void> subscriber = new TestSubscriber<>();
        mTested.hiddenAppsUpdate()
                .subscribe(subscriber);

        mTested.setAppHidden("package4")
                .subscribe();

        subscriber.assertValueCount(1);
        subscriber.assertNoTerminalEvent();
    }

    @Test
    public void shouldAddAppWhenSettingAppHidden() {
        mTested.setAppHidden("package4")
                .subscribe();

        verify(mHiddenPackages).set(anySetOf(String.class));
    }
}