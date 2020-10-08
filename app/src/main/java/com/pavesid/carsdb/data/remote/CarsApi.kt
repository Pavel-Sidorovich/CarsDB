package com.pavesid.carsdb.data.remote

import com.pavesid.carsdb.data.remote.responses.BrandsResponse
import com.pavesid.carsdb.data.remote.responses.ModelsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CarsApi {

    @GET("api/vehicles/getallmakes")
    suspend fun getAllBrands(
        @Query("format") format: String = "json"
    ): Response<BrandsResponse>

    @GET("/api/vehicles/getmodelsformake/{model}")
    suspend fun getModelsForBrand(
        @Path(value = "model", encoded = true) model: String,
        @Query("format") format: String = "json"
    ): Response<ModelsResponse>
}