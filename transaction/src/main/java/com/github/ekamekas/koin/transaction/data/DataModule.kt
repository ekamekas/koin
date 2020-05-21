package com.github.ekamekas.koin.transaction.data

import com.github.ekamekas.koin.transaction.data.repository.RepositoryModule
import com.github.ekamekas.koin.transaction.data.source.DataSourceModule
import dagger.Module

@Module(
    includes = [
        RepositoryModule::class,
        DataSourceModule::class
    ]
)
abstract class DataModule