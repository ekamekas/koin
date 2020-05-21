package com.github.ekamekas.baha.core.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * Module to attach to the application component
 */
@Module(
    includes = [
        ViewModelFactoryModule::class
    ]
)
object CoreModule {

    @JvmStatic
    @Provides
    @Singleton
    @DispatcherMain
    fun provideDispatcherMain(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @JvmStatic
    @Provides
    @Singleton
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}