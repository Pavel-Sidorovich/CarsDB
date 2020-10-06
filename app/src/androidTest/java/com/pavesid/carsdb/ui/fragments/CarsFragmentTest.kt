package com.pavesid.carsdb.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.pavesid.carsdb.R
import com.pavesid.carsdb.adapters.CarItemAdapter
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.getOrAwaitValue
import com.pavesid.carsdb.launchFragmentInHiltContainer
import com.pavesid.carsdb.ui.CarsFragmentFactoryAndroidTest
import com.pavesid.carsdb.ui.MainActivity
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
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

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var testFragmentFactoryAndroidTest: CarsFragmentFactoryAndroidTest

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickAddCarItemButton_navigateToAddCarItemFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<CarsFragment>(fragmentFactory = testFragmentFactoryAndroidTest) {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fab)).perform(click())

        verify(navController).navigate(
            CarsFragmentDirections.actionCarsFragmentToAddCarItemFragment()
        )
    }

//    @Test
//    fun clickSettingsButton_navigateToSettingsFragment() {
//        val navController = mock(NavController::class.java)
//        launchFragmentInContainer<CarsFragment>().onFragment {
//            it.activity?.supportFragmentManager?.fragmentFactory = testFragmentFactoryAndroidTest
//            Navigation.setViewNavController(it.requireView(), navController)
//        }
//
//
//        onView(withId(R.id.action_settings)).perform(click())
////        onView(withText(R.string.settings)).perform(click())
//
//        verify(navController).navigate(
//            CarsFragmentDirections.actionCarsFragmentToSettingsFragment()
//        )
//    }

    @Test
    fun swipeCarItem_deleteItemInDb() {
        val carItem = CarItem(
            "Audi",
            "A4 Allroad",
            "A",
            "petrol",
            "20000",
            1
        )

        var testViewModel: CarsViewModel? = null

        launchFragmentInHiltContainer<CarsFragment>(fragmentFactory = testFragmentFactoryAndroidTest) {
            testViewModel = viewModel
            viewModel?.insertCarItemIntoDb(carItem)
        }

        onView(withId(R.id.recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CarItemAdapter.CarItemViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.carItems?.getOrAwaitValue()).isEmpty()
    }
}