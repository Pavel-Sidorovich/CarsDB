package com.pavesid.carsdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CarItem::class],
    version = 1
)
abstract class CarItemDatabase: RoomDatabase() {

    abstract fun carsDao(): CarsDao
}