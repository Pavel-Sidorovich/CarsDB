package com.pavesid.carsdb.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.data.remote.responses.Brand
import com.pavesid.carsdb.data.remote.responses.BrandsResponse
import com.pavesid.carsdb.data.remote.responses.Model
import com.pavesid.carsdb.data.remote.responses.ModelsResponse
import com.pavesid.carsdb.util.Resource

class FakeCarRepository : CarRepository {

    private val list = mutableListOf<CarItem>()

    private val observableCarItems = MutableLiveData<List<CarItem>>(list)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableCarItems.postValue(list)
    }

    override suspend fun insertCarItem(carItem: CarItem) {
        list.add(carItem)
        refreshLiveData()
    }

    override suspend fun deleteCarItem(carItem: CarItem) {
        list.remove(carItem)
        refreshLiveData()
    }

    override suspend fun updateCarItem(carItem: CarItem) {
        list.removeAll { it.id == carItem.id }
        list.add(carItem)
        refreshLiveData()
    }

    override fun observeAllCarItem(): LiveData<List<CarItem>> = observableCarItems

    override fun observeAllCarItemByModel(): LiveData<List<CarItem>> {
        list.sortBy { it.carModel }
        observableCarItems.postValue(list)
        return observableCarItems
    }

    override fun observeAllCarItemByBrand(): LiveData<List<CarItem>> {
        list.sortBy { it.carBrand }
        observableCarItems.postValue(list)
        return observableCarItems
    }

    override fun observeAllCarItemByPrice(): LiveData<List<CarItem>> {
        list.sortBy { it.carPrice }
        observableCarItems.postValue(list)
        return observableCarItems
    }

    override fun observeAllCarItemByClass(): LiveData<List<CarItem>> {
        list.sortBy { it.carClass }
        observableCarItems.postValue(list)
        return observableCarItems
    }

    override fun observeAllCarItemByEngineType(): LiveData<List<CarItem>> {
        list.sortBy { it.engineType }
        observableCarItems.postValue(list)
        return observableCarItems
    }

    override suspend fun getAllBrands(): Resource<BrandsResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error")
        } else {
            Resource.success(BrandsResponse(0, "", listOf(
                Brand(
                    0,
                    "Opel"
                )
            ), ""))
        }
    }

    override suspend fun getModelsForBrand(model: String): Resource<ModelsResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Error")
        } else {
            Resource.success(ModelsResponse(1, "", listOf(
                Model(
                    0,
                    "Opel",
                    2,
                    "A4"
                )
            ), ""))
        }
    }
}