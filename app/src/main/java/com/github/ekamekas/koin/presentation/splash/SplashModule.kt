package com.github.ekamekas.koin.presentation.splash

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashModule {

    @ContributesAndroidInjector
    abstract fun activitySplash(): SplashActivity

}