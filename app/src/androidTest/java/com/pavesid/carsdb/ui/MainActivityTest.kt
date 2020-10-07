package com.pavesid.carsdb.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.pavesid.carsdb.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MainActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, false, true)

    @Inject
    lateinit var testFragmentFactoryAndroidTest: CarsFragmentFactoryAndroidTest

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickSettingsButton_navigateToSettingsFragment() {

        onView(withId(R.id.action_settings)).perform(click())
        onView(withId(androidx.preference.R.id.recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun clickSettingsButton5TimesThanBack_navigateToCarsFragment() {

        onView(withId(R.id.action_settings)).perform(click())
        onView(withId(R.id.action_settings)).perform(click())
        onView(withId(R.id.action_settings)).perform(click())
        onView(withId(R.id.action_settings)).perform(click())
        onView(withId(R.id.action_settings)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        onView(withId(R.id.carsFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun clickFabButtonThanSettingButton5TimesThanBack_navigateToAddFragment() {

        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.action_settings)).perform(click())
        onView(withId(R.id.action_settings)).perform(click())
        onView(withId(R.id.action_settings)).perform(click())
        onView(withId(R.id.action_settings)).perform(click())
        onView(withId(R.id.action_settings)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        onView(withId(R.id.addFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun pressBackButton_popBackStack() {
        onView(withId(R.id.action_settings)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        onView(withId(R.id.carsFragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}