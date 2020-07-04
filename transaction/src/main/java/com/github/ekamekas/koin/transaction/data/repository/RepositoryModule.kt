package com.github.ekamekas.koin.transaction.data.repository

import com.github.ekamekas.koin.transaction.domain.repository.ITransactionRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    // transaction category
    @Binds
    abstract fun bindTransactionCategoryRepository(repo: TransactionCategoryRepository): ITransactionRepository.TransactionCategory

    // transaction record
    @Binds
    abstract fun bindTransactionRecordRepository(repo: TransactionRecordRepository): ITransactionRepository.TransactionRecord

}