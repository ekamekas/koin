package com.github.ekamekas.koin

import com.github.ekamekas.koin.di.DaggerKoinComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class Koin: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerKoinComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        // setup logging
        if(BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}