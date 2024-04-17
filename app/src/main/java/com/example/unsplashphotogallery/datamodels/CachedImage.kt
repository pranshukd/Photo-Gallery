package com.example.unsplashphotogallery.datamodels
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_images")
data class CachedImage(
    @PrimaryKey val id: String,
    val url: String,
    val imagePath: String
)