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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

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

    private List<App> mList1;
    private List<App> mListEmpty;

    @Before
    public void setup() {
        mTested = new GetAppListUseCase(mAppRepository, mAppSettings);

        mList1 = new ArrayList<>();
        App app = mock(App.class);
        given(app.isGoogleApp()).willReturn(true);
        given(app.isAndroidApp()).willReturn(true);
        given(app.isHidden()).willReturn(true);
        mList1.add(app);
        mListEmpty = Collections.emptyList();

        given(mAppSettings.isDisplayHidden()).willReturn(Observable.just(true));
        given(mAppSettings.isDisplayAndroid()).willReturn(Observable.just(true));
        given(mAppSettings.isDisplayGoogle()).willReturn(Observable.just(true));

        given(mAppRepository.hiddenAppsUpdate()).willReturn(Observable.<Void>never());
        given(mAppRepository.findAppsMarshmallow()).willReturn(Observable.just(mList1));
    }

    @Test
    public void shouldReturnListAndNotComplete() throws Exception {
        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();
        mTested.execute()
                .subscribe(subscriber);

        subscriber.assertValue(mList1);
        subscriber.assertNotCompleted();
    }

    @Test
    public void shouldUpdateListWhenChangesPushed() {
        given(mAppRepository.findAppsMarshmallow()).willReturn(Observable.just(mListEmpty));
        TestScheduler scheduler = new TestScheduler();
        given(mAppRepository.hiddenAppsUpdate()).willReturn(
                Observable.interval(1, TimeUnit.SECONDS, scheduler)
                        .flatMap(new Func1<Long, Observable<Void>>() {
                            @Override
                            public Observable<Void> call(Long aLong) {
                                //change list of apps to return from repository
                                given(mAppRepository.findAppsMarshmallow()).willReturn(Observable.just(mList1));
                                return Observable.just(null);
                            }
                        }));

        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();
        mTested.execute()
                .subscribe(subscriber);

        subscriber.assertValue(mListEmpty);
        subscriber.assertNotCompleted();

        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        //noinspection unchecked
        subscriber.assertValues(mListEmpty, mList1);
        subscriber.assertNotCompleted();
    }

    @Test
    public void shouldUnhideGoogleAppWhenSettingChanged() {
        TestScheduler scheduler = new TestScheduler();
        given(mAppSettings.isDisplayGoogle()).willReturn(
                Observable.interval(1, TimeUnit.SECONDS, scheduler)
                        .flatMap(new Func1<Long, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(Long aLong) {
                                //change setting to display Google
                                return Observable.just(true);
                            }
                        })
                        .startWith(false) //start not displaying Google
        );

        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();
        mTested.execute()
                .subscribe(subscriber);

        subscriber.assertValue(mListEmpty);
        subscriber.assertNotCompleted();

        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        //noinspection unchecked
        subscriber.assertValues(mListEmpty, mList1);
        subscriber.assertNotCompleted();
    }

    @Test
    public void shouldUnhideAndroidAppWhenSettingChanged() {
        TestScheduler scheduler = new TestScheduler();
        given(mAppSettings.isDisplayAndroid()).willReturn(
                Observable.interval(1, TimeUnit.SECONDS, scheduler)
                        .flatMap(new Func1<Long, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(Long aLong) {
                                //change setting to display Android
                                return Observable.just(true);
                            }
                        })
                        .startWith(false) //start not displaying Android
        );

        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();
        mTested.execute()
                .subscribe(subscriber);

        subscriber.assertValue(mListEmpty);
        subscriber.assertNotCompleted();

        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        //noinspection unchecked
        subscriber.assertValues(mListEmpty, mList1);
        subscriber.assertNotCompleted();
    }

    @Test
    public void shouldUnhideHiddenAppWhenSettingChanged() {
        TestScheduler scheduler = new TestScheduler();
        given(mAppSettings.isDisplayHidden()).willReturn(
                Observable.interval(1, TimeUnit.SECONDS, scheduler)
                        .flatMap(new Func1<Long, Observable<Boolean>>() {
                            @Override
                            public Observable<Boolean> call(Long aLong) {
                                //change setting to display hidden
                                return Observable.just(true);
                            }
                        })
                        .startWith(false) //start not displaying hidden apps
        );

        TestSubscriber<List<App>> subscriber = new TestSubscriber<>();
        mTested.execute()
                .subscribe(subscriber);

        subscriber.assertValue(mListEmpty);
        subscriber.assertNotCompleted();

        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        //noinspection unchecked
        subscriber.assertValues(mListEmpty, mList1);
        subscriber.assertNotCompleted();
    }
}