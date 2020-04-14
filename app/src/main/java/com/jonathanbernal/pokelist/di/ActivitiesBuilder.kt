package com.jonathanbernal.pokelist.di

import com.jonathanbernal.pokelist.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesBuilder{
    @ContributesAndroidInjector
    abstract fun bindMainActivity():MainActivity
}