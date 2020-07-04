package com.github.ekamekas.koin.transaction.domain.use_case

import com.github.ekamekas.koin.transaction.domain.use_case.transaction_category.DeleteTransactionCategoryUseCase
import com.github.ekamekas.koin.transaction.domain.use_case.transaction_category.GetTransactionCategoryUseCase
import com.github.ekamekas.koin.transaction.domain.use_case.transaction_category.PutSingleTransactionCategoryUseCase
import com.github.ekamekas.koin.transaction.domain.use_case.transaction_record.DeleteTransactionRecordUseCase
import com.github.ekamekas.koin.transaction.domain.use_case.transaction_record.GetTransactionRecordUseCase
import com.github.ekamekas.koin.transaction.domain.use_case.transaction_record.PutSingleTransactionRecordUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UseCaseModule {

    // transaction category
    @Binds
    @Singleton
    abstract fun bindDeleteTransactionCategoryUseCase(useCase: DeleteTransactionCategoryUseCase): ITransactionUseCase.TransactionCategory.DeleteTransactionCategoryUseCase
    @Binds
    @Singleton
    abstract fun bindGetTransactionCategoryUseCase(useCase: GetTransactionCategoryUseCase): ITransactionUseCase.TransactionCategory.GetTransactionCategoryUseCase
    @Binds
    @Singleton
    abstract fun bindPutSingleTransactionCategoryUseCase(useCase: PutSingleTransactionCategoryUseCase): ITransactionUseCase.TransactionCategory.PutSingleTransactionCategoryUseCase

    // transaction record
    @Binds
    @Singleton
    abstract fun bindDeleteTransactionRecordUseCase(useCase: DeleteTransactionRecordUseCase): ITransactionUseCase.TransactionRecord.DeleteTransactionRecordUseCase
    @Binds
    @Singleton
    abstract fun bindGetTransactionRecordUseCase(useCase: GetTransactionRecordUseCase): ITransactionUseCase.TransactionRecord.GetTransactionRecordUseCase
    @Binds
    @Singleton
    abstract fun bindPutTransactionRecordUseCase(useCase: PutSingleTransactionRecordUseCase): ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase

}