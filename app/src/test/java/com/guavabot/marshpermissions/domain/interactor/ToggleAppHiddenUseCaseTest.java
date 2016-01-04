package com.guavabot.marshpermissions.domain.interactor;

import com.guavabot.marshpermissions.domain.entity.App;
import com.guavabot.marshpermissions.domain.gateway.AppRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * <p>Created by Ivan on 1/4/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ToggleAppHiddenUseCaseTest {

    @Mock
    private AppRepository mAppRepository;
    @Mock
    private App mApp;

    private ToggleAppHiddenUseCase mTested;

    @Before
    public void setup() {
        mTested = new ToggleAppHiddenUseCase(mAppRepository);

        given(mApp.getPackage()).willReturn("package");
    }

    @Test
    public void shouldSetAppHiddenWhenNotHidden() {
        given(mApp.isHidden()).willReturn(false);

        mTested.execute(mApp);

        verify(mAppRepository).setAppHidden("package");
    }

    @Test
    public void shouldSetAppNotHiddenWhenHidden() {
        given(mApp.isHidden()).willReturn(true);

        mTested.execute(mApp);

        verify(mAppRepository).setAppNotHidden("package");
    }

}