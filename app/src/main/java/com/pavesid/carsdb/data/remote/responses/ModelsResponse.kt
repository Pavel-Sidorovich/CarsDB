package com.pavesid.carsdb.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ModelsResponse(
    val Count: Int,
    val Message: String,
    @SerializedName("Results")
    val models: List<Model>,
    val SearchCriteria: String
)