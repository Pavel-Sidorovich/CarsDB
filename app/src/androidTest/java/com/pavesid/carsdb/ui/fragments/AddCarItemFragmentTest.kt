package com.pavesid.carsdb.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.pavesid.carsdb.R
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.getOrAwaitValue
import com.pavesid.carsdb.launchFragmentInHiltContainer
import com.pavesid.carsdb.repositories.FakeCarRepositoryAndroidTest
import com.pavesid.carsdb.ui.CarsFragmentFactoryAndroidTest
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers.hasToString
import org.hamcrest.Matchers.startsWith
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddCarItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: CarsFragmentFactoryAndroidTest

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickInsertIntoDb_carItemInsertedIntoDb() {
        val testViewModel = CarsViewModel(FakeCarRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddCarItemFragment>(fragmentFactory = fragmentFactory) {
            viewModel = testViewModel
        }

        onView(withId(R.id.brandET)).perform(replaceText("Audi"))
        onView(withId(R.id.modelET)).perform(replaceText("A4"))
        onView(withId(R.id.classSpinner)).perform(click())
        onData(hasToString(startsWith("A")))
            .perform(click())
        onView(withId(R.id.engineTypeSpinner)).perform(click())
        onData(hasToString(startsWith("Elec")))
            .perform(click())
        onView(withId(R.id.priceET)).perform(replaceText("342"))

        onView(withId(R.id.applyButton)).perform(click())

        assertThat(testViewModel.carItems.getOrAwaitValue()).contains(
            CarItem(
                carBrand = "Audi",
                carModel = "A4",
                carClass = "A: mini cars",
                engineType = "Electricity",
                carPrice = "342"
            )
        )
    }
}