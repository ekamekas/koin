package com.github.ekamekas.koin.transaction.domain.use_case

import com.github.ekamekas.koin.transaction.domain.use_case.transaction_record.GetTransactionRecordUseCase
import com.github.ekamekas.koin.transaction.domain.use_case.transaction_record.PutSingleTransactionRecordUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class UseCaseModule {

    // transaction record
    @Binds
    @Singleton
    abstract fun bindGetTransactionRecordUseCase(useCase: GetTransactionRecordUseCase): ITransactionUseCase.TransactionRecord.GetTransactionRecordUseCase
    @Binds
    @Singleton
    abstract fun bindPutTransactionRecordUseCase(useCase: PutSingleTransactionRecordUseCase): ITransactionUseCase.TransactionRecord.PutSingleTransactionRecordUseCase

}