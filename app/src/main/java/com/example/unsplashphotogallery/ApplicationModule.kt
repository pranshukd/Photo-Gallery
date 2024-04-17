package com.example.unsplashphotogallery

import android.content.Context
import androidx.room.Room
import com.example.unsplashphotogallery.network.ApiInterface
import com.example.unsplashphotogallery.network.RemoteDataSource
import com.example.unsplashphotogallery.room.AppDatabase
import com.example.unsplashphotogallery.room.CachedImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideApi(
        remoteDataSource: RemoteDataSource
    ): ApiInterface {
        return remoteDataSource.buildApi(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCachedImageDao(database: AppDatabase): CachedImageDao {
        return database.cachedImageDao()
    }

//    @Provides
//    @Singleton
//    fun provideCoroutineScope(): CoroutineScope {
//        return CoroutineScope(SupervisorJob())
//    }

}