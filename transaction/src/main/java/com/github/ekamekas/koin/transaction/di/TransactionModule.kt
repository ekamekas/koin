package com.github.ekamekas.koin.transaction.di

import com.github.ekamekas.koin.transaction.data.DataModule
import com.github.ekamekas.koin.transaction.domain.DomainModule
import com.github.ekamekas.koin.transaction.presentation.PresentationModule
import dagger.Module

@Module(
    includes = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class
    ]
)
abstract class TransactionModule