package com.example.unsplashphotogallery.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unsplashphotogallery.datamodels.CachedImage

@Database(entities = [CachedImage::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cachedImageDao(): CachedImageDao
}