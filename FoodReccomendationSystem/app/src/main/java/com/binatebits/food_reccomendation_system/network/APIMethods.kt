package com.binatebits.food_reccomendation_system.network

import com.binatebits.food_reccomendation_system.data.entities.TotalItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface APIMethods {

    @GET(APIEndPoints.TOTAL_ITEMS)
    suspend fun totalItems() : Response<TotalItemsResponse>


    @GET("get_recomended_items/{item}")
    suspend fun getRecommendedItems(
        @Path("item") item : String,
    ) : Response<TotalItemsResponse>
}