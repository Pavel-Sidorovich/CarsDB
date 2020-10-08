package com.pavesid.carsdb.data.remote.responses

import com.google.gson.annotations.SerializedName

data class BrandsResponse(
    val Count: Int,
    val Message: String,
    @SerializedName("Results")
    val brands: List<Brand>,
    val SearchCriteria: Any
)