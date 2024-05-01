package com.example.convenii.Di

import com.example.convenii.dataSource.RemoteDataSource
import com.example.convenii.repository.AccountRepository
import com.example.convenii.repository.AccountRepositoryImpl
import com.example.convenii.repository.DetailRepository
import com.example.convenii.repository.DetailRepositoryImpl
import com.example.convenii.repository.MainRepository
import com.example.convenii.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAccountRepository(remoteDataSource: RemoteDataSource): AccountRepository =
        AccountRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideMainRepository(remoteDataSource: RemoteDataSource): MainRepository =
        MainRepositoryImpl(remoteDataSource)

    @Provides
    @Singleton
    fun provideDetailRepository(remoteDataSource: RemoteDataSource): DetailRepository =
        DetailRepositoryImpl(remoteDataSource)
}
