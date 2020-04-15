package com.jonathanbernal.pokelist.di



import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import com.jonathanbernal.pokelist.di.DaggerAppComponent
import com.jonathanbernal.pokelist.di.AppComponent
import javax.inject.Inject


@SuppressLint("Registered")
class PokeApplication : Application(), HasActivityInjector {

  @Inject
  lateinit var activityInjector: DispatchingAndroidInjector<Activity>

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

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }


}