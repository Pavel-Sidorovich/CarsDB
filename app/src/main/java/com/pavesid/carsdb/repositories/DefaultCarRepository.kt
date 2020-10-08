package com.pavesid.carsdb.repositories

import androidx.lifecycle.LiveData
import com.pavesid.carsdb.data.local.CarItem
import com.pavesid.carsdb.data.local.CarsDao
import com.pavesid.carsdb.data.remote.CarsApi
import com.pavesid.carsdb.data.remote.responses.BrandsResponse
import com.pavesid.carsdb.data.remote.responses.ModelsResponse
import com.pavesid.carsdb.util.Resource
import javax.inject.Inject

class DefaultCarRepository @Inject constructor(
    private val carsDao: CarsDao,
    private val carsApi: CarsApi
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

    override suspend fun getAllBrands(): Resource<BrandsResponse> {
        return try {
            val response = carsApi.getAllBrands()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Response body is null")
            } else {
                Resource.error("An unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection")
        }
    }

    override suspend fun getModelsForBrand(
        model: String
    ): Resource<ModelsResponse> {
        return try {
            val response = carsApi.getModelsForBrand(model)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Response body is null")
            } else {
                Resource.error("An unknown error occurred")
            }
        } catch (e: Exception) {
            Resource.error("Couldn't reach the server. Check your internet connection")
        }
    }
}