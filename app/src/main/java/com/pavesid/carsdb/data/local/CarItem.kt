package com.pavesid.carsdb.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_items")
data class CarItem(
    val carBrand: String,
    val carModel: String,
    val carClass: String,
    val engineType: String,
    val carPrice: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1
)