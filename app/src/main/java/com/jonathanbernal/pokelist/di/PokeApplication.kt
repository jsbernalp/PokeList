package com.jonathanbernal.pokelist.di



import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PokeApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
    }

}