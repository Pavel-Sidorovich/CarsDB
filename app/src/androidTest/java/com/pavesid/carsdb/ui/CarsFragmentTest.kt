package com.pavesid.carsdb.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.pavesid.carsdb.R
import com.pavesid.carsdb.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class CarsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddCarItemButton_navigateToAddCarItemFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<CarsFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fab)).perform(click())

        verify(navController).navigate(
            CarsFragmentDirections.actionCarsFragmentToAddCarItemFragment()
        )
    }
}