package com.supply.blusip.com.data.di

import androidx.viewbinding.BuildConfig
import com.example.imgurapiapp.StringsConstant.Companion.BASE_URL
import com.google.gson.Gson

import com.supply.blusip.com.data.qulifier.Default
import com.supply.blusip.com.data.qulifier.Token
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val TIME_OUT = 120
    @Singleton
    @Provides
    @Default
    fun provideDefaultRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient()?.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

    @Singleton
    @Provides
    @Token
    fun provideTokenRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient()?.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

    @Singleton
    @Provides
    @Token
    fun httpClient(): OkHttpClient.Builder? {
        val httpClient = OkHttpClient.Builder()
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.readTimeout(
            TIME_OUT.toLong(),
            TimeUnit.SECONDS
        )
        httpClient.writeTimeout(
            TIME_OUT.toLong(),
            TimeUnit.SECONDS
        )
        httpClient.connectTimeout(
            TIME_OUT.toLong(),
            TimeUnit.SECONDS
        )
       /* if (BuildConfig.DEBUG) {

        }*/
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(httpLoggingInterceptor)

        httpClient.addInterceptor(Interceptor { chain ->
            val token = "eb11c2873408a1a80052e15f8444489e90d55245"
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build()
            chain.proceed(request)
        })

        // add your other interceptors …
        // add logging as last interceptor
       // httpClient.addInterceptor(getLoggingInterceptor()!!)
        return httpClient
        /*httpClient.addInterceptor(getLoggingInterceptor());
        return httpClient;*/
    }
    @Singleton
    @Provides
    @Token
     fun getLoggingInterceptor(): HttpLoggingInterceptor? {
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        //logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return logging
    }
}
