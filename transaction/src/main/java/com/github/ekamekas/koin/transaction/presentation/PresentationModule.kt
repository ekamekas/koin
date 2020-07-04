package com.github.ekamekas.koin.transaction.presentation

import com.github.ekamekas.koin.transaction.presentation.transaction_category.TransactionCategoryModule
import com.github.ekamekas.koin.transaction.presentation.transaction_record.TransactionRecordModule
import dagger.Module

@Module(
    includes = [
        TransactionCategoryModule::class,
        TransactionRecordModule::class
    ]
)
abstract class PresentationModule