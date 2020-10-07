package com.pavesid.carsdb.ui.fragments

import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import androidx.appcompat.app.AppCompatDelegate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItem
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.pavesid.carsdb.R
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.getOrAwaitValue
import com.pavesid.carsdb.launchFragmentInHiltContainer
import com.pavesid.carsdb.ui.CarsFragmentFactoryAndroidTest
import com.pavesid.carsdb.ui.viewmodels.CarsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matchers.isA
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SettingsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testFragmentFactoryAndroidTest: CarsFragmentFactoryAndroidTest

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun changeTheme_switchToModeNight() {
        var testViewModel: CarsViewModel? = null
        launchFragmentInHiltContainer<SettingsFragment>(fragmentFactory = testFragmentFactoryAndroidTest) {
            testViewModel = viewModel
        }

        onView(withId(androidx.preference.R.id.recycler_view))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(R.string.dark_mode)), setChecked(false)
                )
            )

        onView(withId(androidx.preference.R.id.recycler_view))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(R.string.dark_mode)), click()
                )
            )

        assertThat(testViewModel?.appTheme?.getOrAwaitValue()).isEqualTo(AppCompatDelegate.MODE_NIGHT_YES)
    }

    @Test
    fun sortByPriceAndThanByEngine_listSortedByPriceAndThanByEngine() {
        var testViewModel: CarsViewModel? = null
        val firstCar = CarItem(
            "Brand_1",
            "Model_2",
            "Class_3",
            "Type_4",
            "Price_5"
        )
        val secondCar = CarItem(
            "Brand_5",
            "Model_4",
            "Class_3",
            "Type_2",
            "Price_1"
        )
        val list = mutableListOf(firstCar, secondCar)
        launchFragmentInHiltContainer<SettingsFragment>(fragmentFactory = testFragmentFactoryAndroidTest) {
            testViewModel = viewModel
            viewModel?.insertCarItemIntoDb(firstCar)
            viewModel?.insertCarItemIntoDb(secondCar)
        }

        onView(withId(androidx.preference.R.id.recycler_view))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(R.string.sort_title)), click()
                )
            )

        onView(withText(R.string.by_price)).perform(click())

        list.sortBy { it.carPrice }
        assertThat(testViewModel?.carItems?.getOrAwaitValue()).isEqualTo(list)

        onView(withId(androidx.preference.R.id.recycler_view))
            .perform(
                actionOnItem<RecyclerView.ViewHolder>(
                    hasDescendant(withText(R.string.sort_title)), click()
                )
            )

        onView(withText(R.string.by_engine_type)).perform(click())

        list.sortBy { it.engineType }
        assertThat(testViewModel?.carItems?.getOrAwaitValue()).isEqualTo(list)
    }

    private fun setChecked(checked: Boolean): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): BaseMatcher<View> {
                return object : BaseMatcher<View>() {
                    override fun matches(item: Any): Boolean {
                        return isA(Checkable::class.java).matches(item)
                    }

                    override fun describeMismatch(item: Any?, mismatchDescription: Description) {}
                    override fun describeTo(description: Description) {}
                }
            }

            override fun getDescription(): String {
                return ""
            }

            override fun perform(uiController: UiController?, view: View) {
                val linearLayout = (view as ViewGroup)
                workForGroup(linearLayout, uiController, view)
            }

            private fun workForGroup(
                viewGroup: ViewGroup,
                uiController: UiController?,
                view: View
            ) {
                val count = viewGroup.childCount - 1
                for (i in 0..count) {
                    if (viewGroup.getChildAt(i) is ViewGroup) {
                        workForGroup(viewGroup.getChildAt(i) as ViewGroup, uiController, view)
                    } else if (viewGroup.getChildAt(i) is Checkable) {
                        if ((viewGroup.getChildAt(i) as Checkable).isChecked != checked)
                            click().perform(uiController, view)
                    }
                }
            }
        }
    }
}