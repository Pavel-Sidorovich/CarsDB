package com.pavesid.carsdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarItem(carItem: CarItem)

    @Delete
    suspend fun deleteCarItem(carItem: CarItem)

    @Update
    suspend fun updateCarItem(carItem: CarItem)

    @Query("SELECT * FROM car_items")
    fun observeAllCarItems(): LiveData<List<CarItem>>

    @Query("SELECT * FROM car_items ORDER BY carModel")
    fun observeAllCarItemByModel(): LiveData<List<CarItem>>

    @Query("SELECT * FROM car_items ORDER BY carBrand")
    fun observeAllCarItemByBrand(): LiveData<List<CarItem>>

    @Query("SELECT * FROM car_items ORDER BY carPrice")
    fun observeAllCarItemByPrice(): LiveData<List<CarItem>>

    @Query("SELECT * FROM car_items ORDER BY carClass")
    fun observeAllCarItemByClass(): LiveData<List<CarItem>>

    @Query("SELECT * FROM car_items ORDER BY engineType")
    fun observeAllCarItemByEngineType(): LiveData<List<CarItem>>
}