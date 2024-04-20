package com.example.convenii.Di

import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.dataSource.RemoteDataSourceImpl
import com.example.convenii.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource =
        RemoteDataSourceImpl(apiService)
}