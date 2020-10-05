package com.pavesid.carsdb.repositories

import androidx.lifecycle.LiveData
import com.pavesid.carsdb.data.local.CarItem

interface CarRepository {

    suspend fun insertCarItem(carItem: CarItem)

    suspend fun deleteCarItem(carItem: CarItem)

    fun observeAllCarItem(): LiveData<List<CarItem>>
}