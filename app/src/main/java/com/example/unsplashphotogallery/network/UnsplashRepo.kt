package com.example.unsplashphotogallery.network

import javax.inject.Inject

class UnsplashRepo @Inject constructor(private val api: ApiInterface) {

    suspend fun getRandomImages(page : Int,perPage : Int) = api.getRandomImages(page,perPage)


}