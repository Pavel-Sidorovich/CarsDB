package com.pavesid.carsdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragment
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.pavesid.carsdb.ui.CarsFragmentFactoryAndroidTest
import com.pavesid.carsdb.ui.MainActivity
import com.pavesid.carsdb.ui.fragments.CarsFragment
import com.pavesid.carsdb.ui.fragments.CarsFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.Mockito

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ExampleInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testFragmentFactoryAndroidTest: CarsFragmentFactoryAndroidTest

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickSettingsButton_navigateToSettingsFragment() {
//        val navController = Mockito.mock(NavController::class.java)
//        launchFragmentInHiltContainer<CarsFragment>(fragmentFactory = testFragmentFactoryAndroidTest) {
//            Navigation.setViewNavController(requireView(), navController)
//        }

        onView(withId(R.id.fab)).perform(ViewActions.click())
        onView(withId(R.id.addFragment)).check(matches(isDisplayed()))
//        onView(withText(R.string.settings)).perform(click())

//        Mockito.verify(navController).navigate(
//            CarsFragmentDirections.actionCarsFragmentToSettingsFragment()
//        )
//        val sc = launchFragmentInContainer<CarsFragment>()
//        sc.onFragment {
////            Navigation.setViewNavController(re)
//        }
    }


}