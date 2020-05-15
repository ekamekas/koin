package com.github.ekamekas.koin.presentation

import com.github.ekamekas.koin.presentation.splash.SplashModule
import dagger.Module

@Module(
    includes = [
        SplashModule::class
    ]
)
abstract class PresentationModule