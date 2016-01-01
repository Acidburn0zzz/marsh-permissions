package com.guavabot.marshpermissions.domain.interactor;

import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;
import com.guavabot.marshpermissions.domain.gateway.AppSettings;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * <p>Created by Ivan on 12/31/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAppListUseCaseTest {

    private GetAppListUseCase mTested;

    @Mock
    private AppRepository mAppRepository;
    @Mock
    private AppSettings mAppSettings;

    @Before
    public void setup() {
        mTested = new GetAppListUseCase(mAppRepository, mAppSettings);
    }

    @Test
    public void shouldReturnEmptyListAndNotComplete() throws Exception {
        when(mAppSettings.isDisplayHidden()).thenReturn(Observable.just(true));
        when(mAppSettings.isDisplayAndroid()).thenReturn(Observable.just(true));
        when(mAppSettings.isDisplayGoogle()).thenReturn(Observable.just(true));
        when(mAppRepository.hiddenAppsUpdate()).thenReturn(Observable.<Void>never());
        List<App> list = new ArrayList<>();
        when(mAppRepository.findAppsMarshmallow()).thenReturn(Observable.just(list));
        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();

        mTested.execute()
                .subscribe(subscriber);

        subscriber.assertValue(list);
        subscriber.assertNotCompleted();
    }

    @Test
    public void shouldUpdateListWhenChangesPushed() {
        when(mAppSettings.isDisplayHidden()).thenReturn(Observable.just(true));
        when(mAppSettings.isDisplayAndroid()).thenReturn(Observable.just(true));
        when(mAppSettings.isDisplayGoogle()).thenReturn(Observable.just(true));
        List<App> list = new ArrayList<>();
        when(mAppRepository.findAppsMarshmallow()).thenReturn(Observable.just(list));
        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();

        final List<App> list2 = new ArrayList<>();
        App app = mock(App.class);
        when(app.getPackage()).thenReturn("com");
        when(app.isHidden()).thenReturn(false);
        list2.add(app);
        TestScheduler scheduler = new TestScheduler();
        when(mAppRepository.hiddenAppsUpdate()).thenReturn(
                Observable.interval(1, TimeUnit.SECONDS, scheduler)
                        .flatMap(new Func1<Long, Observable<Void>>() {
                            @Override
                            public Observable<Void> call(Long aLong) {
                                //change list of apps to return from repository
                                when(mAppRepository.findAppsMarshmallow()).thenReturn(Observable.just(list2));
                                return Observable.just(null);
                            }
                        }));

        mTested.execute()
                .subscribe(subscriber);

        subscriber.assertValue(list);
        subscriber.assertNotCompleted();

        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        //noinspection unchecked
        subscriber.assertValues(list, list2);
        subscriber.assertNotCompleted();
    }
}