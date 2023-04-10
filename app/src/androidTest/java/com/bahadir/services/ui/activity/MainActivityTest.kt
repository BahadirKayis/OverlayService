package com.bahadir.services.ui.activity

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.bahadir.services.R
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    private lateinit var activityScenario: ActivityScenario<MainActivity>


    init {
        AccessibilityChecks.enable()
    }


    @Before
    fun setUp() {
        activityScenario = ActivityScenario.launch(MainActivity::class.java)
        activityScenario.moveToState(Lifecycle.State.RESUMED)


    }

    @Test
    fun testAddAndViewSpend() {
        onView(withId(R.id.btn_service_control)).perform(click())
        onView(withId(R.id.other_app_design)).check(matches(isDisplayed()))

//        onView(withId(R.id.text_app_name)).check(matches(withText("com.bahadir.services")))
//        val instrumentation = InstrumentationRegistry.getInstrumentation()
//        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_POWER)
//        instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_ENTER)

    }

    @After
    fun tearDown() {
    }
}