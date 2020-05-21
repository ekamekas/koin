package com.github.ekamekas.koin.transaction.domain

import com.github.ekamekas.koin.transaction.domain.use_case.UseCaseModule
import com.github.ekamekas.koin.transaction.domain.validator.ValidatorModule
import dagger.Module

@Module(
    includes = [
        UseCaseModule::class,
        ValidatorModule::class
    ]
)
abstract class DomainModule