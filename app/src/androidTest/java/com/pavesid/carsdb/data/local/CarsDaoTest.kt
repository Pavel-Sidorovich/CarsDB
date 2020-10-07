package com.pavesid.carsdb.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.pavesid.carsdb.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CarsDaoTest {

    /**
     * Need to execute code one after another in this class. Otherwise it will be
     * java.lang.IllegalStateException: This job has not completed yet.
     */
    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var database: CarItemDatabase
    private lateinit var dao: CarsDao

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        dao = database.carsDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCarItem() = runBlockingTest {
        val carItem = CarItem(
            "Audi",
            "A4 Allroad",
            "A",
            "petrol",
            "20000",
            1
        )
        dao.insertCarItem(carItem)

        val allCarsItem = dao.observeAllCarItems().getOrAwaitValue()

        assertThat(allCarsItem).contains(carItem)
    }

    @Test
    fun deleteCarItem() = runBlockingTest {
        val carItem = CarItem(
            "Audi",
            "A4 Allroad",
            "A",
            "petrol",
            "20000",
            1
        )
        dao.insertCarItem(carItem)
        dao.deleteCarItem(carItem)

        val allShoppingItems = dao.observeAllCarItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(carItem)
    }
}