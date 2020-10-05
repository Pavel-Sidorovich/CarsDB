package com.pavesid.carsdb.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarItem(carItem: CarItem)

    @Delete
    suspend fun deleteCarItem(carItem: CarItem)

    @Query("SELECT * FROM car_items")
    fun observeAllCarItems(): LiveData<List<CarItem>>
}