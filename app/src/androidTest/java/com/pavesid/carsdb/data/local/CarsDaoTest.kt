package com.pavesid.carsdb.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.pavesid.carsdb.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class) // For working with emulator
@SmallTest
class CarsDaoTest {

    /** Need to execute code one after another in this class. Otherwise it will be
    java.lang.IllegalStateException: This job has not completed yet. */
    @get:Rule
    var instanceTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: CarItemDatabase
    private lateinit var dao: CarsDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CarItemDatabase::class.java
        ).allowMainThreadQueries()
            .build()
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