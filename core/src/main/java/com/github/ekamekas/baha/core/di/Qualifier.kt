package com.github.ekamekas.baha.core.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import javax.inject.Qualifier
import kotlin.reflect.KClass

/**
 * Qualifier to indicate instance is local data source
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalDataSource

/**
 * Qualifier to indicate instance is remote data source
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class RemoteDataSource

/**
 * Qualifier to indicate instance is cache data source
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CacheDataSource

/**
 * Qualifier to indicate instance is main dispatcher
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherMain

/**
 * Qualifier to indicate instance is IO dispatcher
 */
@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DispatcherIO

/**
 * Class to annotate class as key for dependency injection
 */
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)