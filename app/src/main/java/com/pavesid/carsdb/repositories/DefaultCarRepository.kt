package com.pavesid.carsdb.repositories

import androidx.lifecycle.LiveData
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.data.local.CarsDao
import javax.inject.Inject

class DefaultCarRepository @Inject constructor(
    private val carsDao: CarsDao
) : CarRepository {
    override suspend fun insertCarItem(carItem: CarItem) {
        carsDao.insertCarItem(carItem)
    }

    override suspend fun deleteCarItem(carItem: CarItem) {
        carsDao.deleteCarItem(carItem)
    }

    override suspend fun updateCarItem(carItem: CarItem) {
        carsDao.updateCarItem(carItem)
    }

    override fun observeAllCarItem(): LiveData<List<CarItem>> = carsDao.observeAllCarItems()

    override fun observeAllCarItemByModel(): LiveData<List<CarItem>> =
        carsDao.observeAllCarItemByModel()

    override fun observeAllCarItemByBrand(): LiveData<List<CarItem>> =
        carsDao.observeAllCarItemByBrand()

    override fun observeAllCarItemByPrice(): LiveData<List<CarItem>> =
        carsDao.observeAllCarItemByPrice()

    override fun observeAllCarItemByClass(): LiveData<List<CarItem>> =
        carsDao.observeAllCarItemByClass()

    override fun observeAllCarItemByEngineType(): LiveData<List<CarItem>> =
        carsDao.observeAllCarItemByEngineType()
}