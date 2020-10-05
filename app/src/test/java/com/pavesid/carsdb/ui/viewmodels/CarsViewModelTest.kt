package com.pavesid.carsdb.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.pavesid.carsdb.MainCoroutineRule
import com.pavesid.carsdb.getOrAwaitValueTest
import com.pavesid.carsdb.repositories.FakeCarRepository
import com.pavesid.carsdb.util.Constants
import com.pavesid.carsdb.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CarsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /** Need to execute coroutines code. Otherwise it will be
     * Module with the Main dispatcher had failed to initialize. For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used
     */
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CarsViewModel

    @Before
    fun setup() {
        viewModel = CarsViewModel(FakeCarRepository())
    }

    @Test
    fun `insert car item with empty field, return error`() {
        viewModel.insertCarItem("Audi", "", "A", "petrol", "20000")

        val value = viewModel.insertCarItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert car item with too long model name, return error`() {
        val model = buildString {
            for (i in 0..Constants.MAX_MODEL_NAME_LENGTH) {
                append(1)
            }
        }
        viewModel.insertCarItem("Audi", model, "A", "petrol", "20000")

        val value = viewModel.insertCarItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert car item with too long brand name, return error`() {
        val brand = buildString {
            for (i in 0..Constants.MAX_BRAND_NAME_LENGTH) {
                append(1)
            }
        }
        viewModel.insertCarItem(brand, "A4 Allroad", "A", "petrol", "20000")

        val value = viewModel.insertCarItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert car item with too long price, return error`() {
        val price = buildString {
            for (i in 0..Constants.MAX_PRICE_LENGTH) {
                append(1)
            }
        }
        viewModel.insertCarItem("Audi", "A4 Allroad", "A", "petrol", price)

        val value = viewModel.insertCarItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert car item with valid input, return success`() {
        viewModel.insertCarItem("Audi", "A4 Allroad", "A", "petrol", "2")

        val value = viewModel.insertCarItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}