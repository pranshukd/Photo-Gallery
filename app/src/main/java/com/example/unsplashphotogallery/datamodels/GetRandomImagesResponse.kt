package com.example.unsplashphotogallery.datamodels


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

class GetRandomImagesResponse : ArrayList<GetRandomImagesResponse.SplashImage>(){
    @Keep
    data class SplashImage(
        @SerializedName("id") val id: String?,
        @SerializedName("urls") val urls: Urls?,
        @SerializedName("user") val user: User?
    ) {
        @Keep
        data class Urls(
            @SerializedName("regular") val regular: String?
        )
    
        @Keep
        data class User(
            @SerializedName("name") val name: String?
        )
    }
}