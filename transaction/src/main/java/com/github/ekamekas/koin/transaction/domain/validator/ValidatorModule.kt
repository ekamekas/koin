package com.github.ekamekas.koin.transaction.domain.validator

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ValidatorModule {

    // transaction category
    @Binds
    @Singleton
    abstract fun bindTransactionCategoryValidator(validator: TransactionCategoryValidator): ITransactionValidator.TransactionCategory

    // transaction record
    @Binds
    @Singleton
    abstract fun bindTransactionRecordValidator(validator: TransactionRecordValidator): ITransactionValidator.TransactionRecord

}