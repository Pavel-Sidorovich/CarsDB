package com.pavesid.carsdb.repositories

import androidx.lifecycle.LiveData
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.data.remote.responses.BrandsResponse
import com.pavesid.carsdb.data.remote.responses.ModelsResponse
import com.pavesid.carsdb.util.Resource
import retrofit2.Response

interface CarRepository {

    suspend fun insertCarItem(carItem: CarItem)

    suspend fun deleteCarItem(carItem: CarItem)

    suspend fun updateCarItem(carItem: CarItem)

    fun observeAllCarItem(): LiveData<List<CarItem>>

    fun observeAllCarItemByModel(): LiveData<List<CarItem>>

    fun observeAllCarItemByBrand(): LiveData<List<CarItem>>

    fun observeAllCarItemByPrice(): LiveData<List<CarItem>>

    fun observeAllCarItemByClass(): LiveData<List<CarItem>>

    fun observeAllCarItemByEngineType(): LiveData<List<CarItem>>

    suspend fun getAllBrands(): Resource<BrandsResponse>

    suspend fun getModelsForBrand(
        model: String
    ): Resource<ModelsResponse>
}