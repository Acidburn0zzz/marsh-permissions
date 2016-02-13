package com.guavabot.marshpermissions.domain.interactor;

import com.guavabot.marshpermissions.domain.gateway.AppRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * <p>Created by Ivan on 1/4/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ToggleAppHiddenUseCaseTest {

    @Mock
    private AppRepository mAppRepository;

    private ToggleAppHiddenUseCase mTested;

    @Before
    public void setup() {
        mTested = new ToggleAppHiddenUseCase(mAppRepository);
    }

    @Test
    public void shouldSetAppHiddenWhenNotHidden() {

        mTested.execute("package", true);

        verify(mAppRepository).setAppHidden("package");
    }

    @Test
    public void shouldSetAppNotHiddenWhenHidden() {

        mTested.execute("package", false);

        verify(mAppRepository).setAppNotHidden("package");
    }

}