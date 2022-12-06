package com.binatebits.food_reccomendation_system.data.remote

import com.binatebits.food_reccomendation_system.network.APIMethods
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: APIMethods
): BaseDataSource() {

    suspend fun totalItems() = getResult { apiService.totalItems() }
    suspend fun getRecommendedItems(item : String ) = getResult { apiService.getRecommendedItems(item) }
}