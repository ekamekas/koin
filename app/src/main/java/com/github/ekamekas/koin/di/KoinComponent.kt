package com.github.ekamekas.koin.di

import android.content.Context
import com.github.ekamekas.baha.core.di.CoreModule
import com.github.ekamekas.koin.Koin
import com.github.ekamekas.koin.transaction.di.TransactionModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        KoinModule::class,
        CoreModule::class,
        TransactionModule::class
    ]
)
interface KoinComponent: AndroidInjector<Koin> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): KoinComponent
    }

}
