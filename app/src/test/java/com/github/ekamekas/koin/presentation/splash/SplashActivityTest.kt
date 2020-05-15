package com.github.ekamekas.koin.presentation.splash

import android.os.Build
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.ekamekas.koin.R
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(maxSdk = Build.VERSION_CODES.P)
class SplashActivityTest {

    @Test
    fun brandImageShouldVisible() {
        // arrange
        ActivityScenario.launch(SplashActivity::class.java)
        // act
        // assert
        onView(withId(R.id.ivBrand)).check(matches(isDisplayed()))
    }

}