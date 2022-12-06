package com.binatebits.food_reccomendation_system.di

import com.binatebits.food_reccomendation_system.data.remote.RemoteDataSource
import com.binatebits.food_reccomendation_system.network.APIMethods
import com.binatebits.food_reccomendation_system.network.RestConfig
import com.binatebits.food_reccomendation_system.network.RestConfig.Companion.API_SERVER
import com.binatebits.food_reccomendation_system.network.RestConfig.Companion.API_TOKEN
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(API_SERVER)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient =
        if (RestConfig.DEBUG) { // debug ON
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().apply {
                addInterceptor { chain ->
                    val request = chain.request()
                    val builder = request
                        .newBuilder()
                        .header("TOKEN", API_TOKEN)
                        .method(request.method, request.body)
                    val mutatedRequest = builder.build()
                    val response = chain.proceed(mutatedRequest)
                    response
                }
                addInterceptor(logger)
                readTimeout(100, TimeUnit.SECONDS)
                connectTimeout(100, TimeUnit.SECONDS)
            }.build()
        } else // debug OFF
            OkHttpClient.Builder().apply {
                addInterceptor { chain ->
                    val request = chain.request()
                    val builder = request
                        .newBuilder()
                        .header("TOKEN", API_TOKEN)
                        .method(request.method, request.body)
                    val mutatedRequest = builder.build()
                    val response = chain.proceed(mutatedRequest)
                    response
                }
                readTimeout(100, TimeUnit.SECONDS)
                connectTimeout(100, TimeUnit.SECONDS)
            }.build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()



    @Provides
    fun provideApiService(retrofit: Retrofit): APIMethods = retrofit.create(APIMethods::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: APIMethods) = RemoteDataSource(apiService)
}