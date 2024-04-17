package com.example.unsplashphotogallery.datamodels


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

class GetRandomImagesResponse : ArrayList<GetRandomImagesResponse.UnsplashImage>(){
    @Keep
    data class UnsplashImage(
        @SerializedName("id") val id: String?,
        @SerializedName("urls") val urls: Urls?,
    ) {
        @Keep
        data class Urls(
            @SerializedName("regular") val regular: String?
        )
    }
}