package com.binatebits.food_reccomendation_system

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binatebits.food_reccomendation_system.data.entities.TotalItemsResponse
import com.binatebits.food_reccomendation_system.data.remote.RemoteDataSource
import com.binatebits.food_reccomendation_system.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteDataSource: RemoteDataSource
)  : ViewModel() {

    private val _responseTotalItems = MutableLiveData<Resource<TotalItemsResponse>>()
    val responseTotalItems = _responseTotalItems

    private val _responseRecommendedItems = MutableLiveData<Resource<TotalItemsResponse>>()
    val responseRecommendedItems = _responseRecommendedItems

    fun getTotalItems()
    {    viewModelScope.launch {
        responseTotalItems.value  = remoteDataSource.totalItems()
    }
    }

    fun getRecommendedItems(item:String)
    {    viewModelScope.launch {
        responseRecommendedItems.value  = remoteDataSource.getRecommendedItems(item)
    }
    }



}