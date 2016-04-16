package com.guavabot.marshpermissions.domain.interactor;

import com.guavabot.marshpermissions.domain.entity.App;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * <p>Created by Ivan on 1/4/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAppListFilteredUseCaseTest {

    private GetAppListFilteredUseCase mTested;

    @Mock
    private GetAppListUseCase mGetAppListUseCase;
    private App mApp1;
    private App mApp2;

    @Before
    public void setUp() throws Exception {
        mTested = new GetAppListFilteredUseCase(mGetAppListUseCase);

        final List<App> apps = new ArrayList<>();
        mApp1 = mock(App.class);
        given(mApp1.getPackage()).willReturn("package1");
        apps.add(mApp1);
        mApp2 = mock(App.class);
        given(mApp2.getPackage()).willReturn("package2");
        apps.add(mApp2);
        given(mGetAppListUseCase.execute())
                .willReturn(Observable.create(new Observable.OnSubscribe<List<App>>() {
                    @Override
                    public void call(Subscriber<? super List<App>> subscriber) {
                        subscriber.onNext(apps);
                    }
                }));
    }

    @Test
    public void shouldReturnFullListIfFilterNull() {
        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();

        mTested.execute(null).subscribe(subscriber);

        subscriber.assertNoTerminalEvent();
        subscriber.assertValueCount(1);
        List<App> result = subscriber.getOnNextEvents().get(0);
        assertThat(result).containsOnly(mApp1, mApp2);
    }

    @Test
    public void shouldReturnFullListIfFilterEmpty() {
        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();

        mTested.execute("").subscribe(subscriber);

        subscriber.assertNoTerminalEvent();
        subscriber.assertValueCount(1);
        List<App> result = subscriber.getOnNextEvents().get(0);
        assertThat(result).containsOnly(mApp1, mApp2);
    }

    @Test
    public void shouldIncludeAppOnlyIfTextContained() {
        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();

        mTested.execute("age2").subscribe(subscriber);

        subscriber.assertNoTerminalEvent();
        subscriber.assertValueCount(1);
        List<App> result = subscriber.getOnNextEvents().get(0);
        assertThat(result).containsOnly(mApp2);
    }

}