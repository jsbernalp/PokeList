package com.jonathanbernal.pokelist.di

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.jonathanbernal.data.PokemonApi
import com.jonathanbernal.data.PokemonEndPoint
import com.jonathanbernal.data.mapper.PokemonMapper
import com.jonathanbernal.data.repository.PokemonRepositoryImpl
import com.jonathanbernal.domain.repository.PokemonRepository
import com.jonathanbernal.pokelist.network.PokeApiService
import com.jonathanbernal.pokelist.viewmodel.PokemonViewModelFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
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
            .baseUrl("http://pokeapi.co/api/v2/")
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

    @Provides
    @Singleton
    @JvmStatic
    fun providePokeApiService(retrofit: Retrofit): PokeApiService {
        return retrofit.create(PokeApiService::class.java)
    }


    @Provides
    @Singleton
    @JvmStatic
    fun providePokeApiEndpointService(retrofit: Retrofit): PokemonEndPoint {
        return retrofit.create(PokemonEndPoint::class.java)
    }


    @Provides
    @Singleton
    @JvmStatic
    fun providesCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    @JvmStatic
    fun providePokemonViewModelFactory(factory: PokemonViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    @JvmStatic
    fun providePokemonRepository(pokemonApi: PokemonApi, pokemonMapper: PokemonMapper): PokemonRepository {
        return PokemonRepositoryImpl(pokemonApi, pokemonMapper)
    }


}