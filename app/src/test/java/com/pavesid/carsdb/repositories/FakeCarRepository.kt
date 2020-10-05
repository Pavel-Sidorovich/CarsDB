package com.pavesid.carsdb.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavesid.carsdb.data.local.CarItem
import javax.inject.Inject

class FakeCarRepository : CarRepository {

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

    override fun observeAllCarItem(): LiveData<List<CarItem>> = observableCarItems
}