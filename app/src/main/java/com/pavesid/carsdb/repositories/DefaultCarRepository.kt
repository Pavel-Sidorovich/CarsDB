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

    override fun observeAllCarItem(): LiveData<List<CarItem>> = carsDao.observeAllCarItems()
}