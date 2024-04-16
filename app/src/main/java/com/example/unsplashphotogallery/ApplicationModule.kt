package com.example.unsplashphotogallery

import com.example.unsplashphotogallery.network.ApiInterface
import com.example.unsplashphotogallery.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}