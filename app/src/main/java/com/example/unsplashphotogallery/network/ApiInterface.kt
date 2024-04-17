package com.example.unsplashphotogallery.network

import com.example.unsplashphotogallery.datamodels.GetRandomImagesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

//    @GET("photos/random")
    @GET("photos")
    suspend fun  getRandomImages(@Query("page") page : Int, @Query("per_page") perPage : Int): GetRandomImagesResponse

}