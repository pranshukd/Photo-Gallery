package com.example.unsplashphotogallery.network

import com.example.unsplashphotogallery.datamodels.GetRandomImagesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("photos/random")
    suspend fun  getRandomImages(@Query("count") count : Int): GetRandomImagesResponse

}