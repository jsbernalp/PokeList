package com.jonathanbernal.pokelist.di



import android.app.Application


class PokeApplication : Application(){

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        initComponent()
    }

    private fun initComponent() {
        component = DaggerAppComponent.builder()
            .application(this)
            .build()

        component.inject(this)
    }

}