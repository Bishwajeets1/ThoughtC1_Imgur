package com.example.imgurapiapp.di

import android.content.Context
import com.example.imgurapiapp.api.ImgurApiService
import com.example.imgurapiapp.repository.DefaultImgurRepository
import com.example.imgurapiapp.repository.ImgurRepository
import com.example.imgurapiapp.source.DefaultImgurDataSource
import com.example.imgurapiapp.source.ImgurDataSource
import com.supply.blusip.com.data.qulifier.Default
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ImgurModule {

    @Singleton
    @Provides
    fun provideImgurApiService(@Default  retrofit: Retrofit) : ImgurApiService{
        return retrofit.create(ImgurApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideImgurRepository(
        @ApplicationContext applicationContext: Context,
        remote:ImgurDataSource
    ):ImgurRepository{
        return DefaultImgurRepository(applicationContext, remote)
    }

    @Provides
    @Singleton
    fun provideImgurDataSource(
        @ApplicationContext applicationContext: Context,
        api :ImgurApiService
    ):ImgurDataSource{
        return DefaultImgurDataSource(applicationContext, api)
    }
}