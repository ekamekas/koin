package com.github.ekamekas.baha.core.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Module to attach to the application component
 */
@Module(
    includes = [
        ViewModelFactoryModule::class
    ]
)
abstract class CoreModule