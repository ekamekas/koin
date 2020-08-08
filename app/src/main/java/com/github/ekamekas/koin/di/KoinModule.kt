package com.github.ekamekas.koin.di

import com.github.ekamekas.koin.presentation.PresentationModule
import dagger.Module

@Module(
    includes = [
        PresentationModule::class
    ]
)
abstract class KoinModule