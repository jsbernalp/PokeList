package com.jonathanbernal.pokelist.di

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideContext(application: PokeApplication): Context = application.applicationContext

    @Singleton
    @Provides
    @JvmStatic
    fun provideApplication(application: PokeApplication): Application = application

    @Singleton
    @Provides
    @JvmStatic
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    @JvmStatic
    fun provideOkhttpClient(interceptor: Interceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()

        okHttpClientBuilder.addInterceptor(interceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)

        return okHttpClientBuilder.build()
    }


    @Provides
    @Singleton
    @JvmStatic
    fun provideLoggingInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.e("SERVER",message)
            }
        })
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

}