package com.github.ekamekas.koin.presentation

import com.github.ekamekas.koin.presentation.dashboard.DashboardModule
import com.github.ekamekas.koin.presentation.splash.SplashModule
import dagger.Module

@Module(
    includes = [
        DashboardModule::class,
        SplashModule::class
    ]
)
abstract class PresentationModule