package com.pavesid.carsdb.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavesid.carsdb.data.local.CarItem

class FakeCarRepositoryAndroidTest : CarRepository {

    private val list = mutableListOf<CarItem>()

    private val observableCarItems = MutableLiveData<List<CarItem>>(list)

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
}