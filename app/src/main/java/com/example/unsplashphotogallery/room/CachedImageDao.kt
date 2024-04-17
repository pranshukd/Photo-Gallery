package com.example.unsplashphotogallery.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.unsplashphotogallery.datamodels.CachedImage

@Dao
interface CachedImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: CachedImage)

    @Query("SELECT * FROM cached_images WHERE id = :id")
    suspend fun getImage(id: String): CachedImage?
}